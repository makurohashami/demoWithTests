package com.example.demowithtests.service.employeeService;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Avatar;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.service.fileManagerService.FileManagerService;
import com.example.demowithtests.service.workPassService.WorkPassService;
import com.example.demowithtests.util.annotations.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.Name;
import com.example.demowithtests.util.annotations.ToLowerCase;
import com.example.demowithtests.util.exception.ImageException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceUnavailableException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Log4j2
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final FileManagerService fileManagerService;
    private final WorkPassService passService;

    @Override
    @ActivateCustomAnnotations({ToLowerCase.class, Name.class})
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        var employees = employeeRepository.findAll();
        employees.forEach(this::setOnlyActiveAddresses);
        employees.forEach(this::checkPassIsAvailable);
        return employees;
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll(pageable);
        page.map(employee -> {
            setOnlyActiveAddresses(employee);
            checkPassIsAvailable(employee);
            return employee;
        });
        return page;
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        checkPassIsAvailable(employee);
        changeVisibleStatus(employee);
        setOnlyActiveAddresses(employee);
        setOnlyNotExpiredAvatars(employee);
        if (!employee.getIsVisible()) throw new ResourceUnavailableException();
        return employee;
    }

    private void checkPassIsAvailable(Employee employee) {
        if (employee.getWorkPass() != null && employee.getWorkPass().getIsDeleted().equals(Boolean.TRUE)) {
            employee.setWorkPass(null);
            employeeRepository.save(employee);
        }
    }

    private void changeVisibleStatus(Employee employee) {
        if (employee.getIsVisible() == null) {
            employee.setIsVisible(Boolean.TRUE);
            employeeRepository.save(employee);
        }
    }

    @Override
    @Transactional
    @ActivateCustomAnnotations({ToLowerCase.class, Name.class})
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (isFieldNew(entity.getName(), employee.getName()))
                        entity.setName(employee.getName());
                    if (isFieldNew(entity.getEmail(), employee.getEmail()))
                        entity.setEmail(employee.getEmail());
                    if (isFieldNew(entity.getCountry(), employee.getCountry()))
                        entity.setCountry(employee.getCountry());
                    if (isFieldNew(entity.getGender(), employee.getGender()))
                        entity.setGender(employee.getGender());
                    if (isFieldNew(entity.getAddresses(), employee.getAddresses())) {
                        entity.getAddresses().forEach(address -> address.setAddressHasActive(Boolean.FALSE));
                        entity.setAddresses(Stream
                                .concat(entity.getAddresses().stream(),
                                        employee.getAddresses().stream())
                                .collect(Collectors.toSet()));
                    }
                    employeeRepository.save(entity);
                    return getById(id);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private boolean isFieldNew(Object existField, Object newField) {
        return newField != null && !newField.equals(existField);
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        employee.setIsVisible(Boolean.FALSE);
        employee.getAddresses().forEach(address -> address.setAddressHasActive(Boolean.FALSE));
        passService.removePass(employee.getWorkPass().getId());
        employeeRepository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }


    //-- Методи Олега --\\
    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }


    //-- Мої методи --\\
    @Override
    public List<Employee> getByGender(Gender gender, String country) {
        var employees = employeeRepository.findByGender(gender.toString(), country);
        return employees;
    }

    @Override
    public Page<Employee> getActiveAddressesByCountry(String country, Pageable pageable) {
        return employeeRepository.findAllWhereIsActiveAddressByCountry(country, pageable);
    }

    @Override
    public List<Employee> selectWhereIsVisibleIsNull() {
        var employees = employeeRepository.queryEmployeeByIsVisibleIsNull();
        employees.forEach(employee -> employee.setIsVisible(Boolean.TRUE));
        employeeRepository.saveAll(employees);
        return employeeRepository.queryEmployeeByIsVisibleIsNull();
    }

    @Override
    public void addOneThousandEmployees() {
        List<Employee> employees = Collections.nCopies(1000, Employee.builder()
                .name("Yan")
                .email("yan@gmail.com")
                .country("Poland")
                .gender(Gender.M)
                .isVisible(Boolean.TRUE)
                .build()
        );
        employeeRepository.saveAll(employees);
    }

    @Override
    public void updateOneKEmployee(Employee employee) {
        Long methodStart = System.currentTimeMillis();
        Integer maxId = employeeRepository.findMaxEmployeeId();
        for (int i = maxId - 999; i <= maxId; ++i) {
            updateById(i, employee);
        }
        Long diff = System.currentTimeMillis() - methodStart;
        log.info("updateOneKEmployee() Service - emd: time in ms - {} ", diff);
    }

    //Дістає всіх користувачів в яких є фото яким 5 років чи більше.
    @Override
    public List<Employee> findExpiredAvatars() {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getAvatars().stream()
                        .anyMatch(avatar -> !avatar.getIsExpired()
                                && avatar.getCreationDate().plusYears(5).isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    //Робить аватарки користувача неактивними та відсилає повідомлення на пошти користувачів з оновити фото.
    @Override
    public Set<String> sendEmailsWhereExpiredPhotos() {
        var expired = findExpiredAvatars();
        var emails = new HashSet<String>();
        if (!expired.isEmpty()) {
            expired.forEach(employee -> {
                emailSenderService.sendEmail(
                        /*employee.getEmail(),*/ //підставляються адреси користувачів
                        "yaroslv.kotyk@gmail.com", //підставив свою для тесту
                        "Your photo was deleted. Need to add a new the photo",
                        String.format(
                                "Dear " + employee.getName() + "!\n" +
                                        "\n" +
                                        "Your photo expired.\n" +
                                        "We had to delete it. Please upload a new photo. \n" +
                                        "\n" +
                                        "Best regards,\n" +
                                        "DemoApp Profile Service.")
                );
                expireEmployeesAvatars(employee);
                emails.add(employee.getEmail());
            });
        }
        return emails;
    }

    @Override
    public Employee saveAvatarToEmployee(Integer id, MultipartFile img) throws IOException {
        validateImage(img);
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        var fileName = fileManagerService.saveFile(img);
        expireEmployeesAvatars(employee);
        employee.getAvatars().add(new Avatar(fileName));
        return employeeRepository.save(employee);
    }

    @Override
    public byte[] findEmployeesAvatar(Integer id) throws Exception {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        var list = employee.getAvatars()
                .stream()
                .filter(avatar -> avatar.getIsExpired().equals(Boolean.FALSE))
                .collect(Collectors.toList());
        if (list.isEmpty())
            throw new ResourceNotFoundException();
        return fileManagerService.getFile(list.get(0).getImgName());
    }

    @Override
    public void removeEmployeesAvatar(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        expireEmployeesAvatars(employee);
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findWhereAvatarWillExpireSoon() {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getAvatars().stream()
                        .anyMatch(avatar -> !avatar.getIsExpired()
                                && avatar.getCreationDate().plusYears(5).minusDays(7).isBefore(LocalDateTime.now())
                                && avatar.getCreationDate().plusYears(5).isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> sendEmailsWhereAvatarWillExpireSoon() {
        var expired = findWhereAvatarWillExpireSoon();
        var emails = new HashSet<String>();
        if (!expired.isEmpty()) {
            expired.forEach(employee -> {
                emailSenderService.sendEmail(
                        /*employee.getEmail(),*/ //підставляються адреси користувачів
                        "yaroslv.kotyk@gmail.com", //підставив свою для тесту
                        "Need to update the photo",
                        String.format(
                                "Dear " + employee.getName() + "!\n" +
                                        "\n" +
                                        "The expiration date of your photo is coming up soon. \n" +
                                        "Please. Don't delay in updating it. \n" +
                                        "\n" +
                                        "Best regards,\n" +
                                        "DemoApp Profile Service.")
                );
                emails.add(employee.getEmail());
            });
        }
        return emails;
    }

    //-- Technical Methods --\\

    private void validateImage(MultipartFile img) {
        if (img.isEmpty()) throw new ImageException("File is empty");
        if (!img.getContentType().equals("image/jpeg")) throw new ImageException("Image must be a JPEG");
        try {
            InputStream is = img.getInputStream();
            BufferedImage image = ImageIO.read(is);
            if (image.getWidth() > 500 || image.getHeight() > 500)
                throw new ImageException("Image must be max a 500x500 resolution. Image is "
                        + image.getWidth() + "x" + image.getHeight());
            if (image.getWidth() < 100 || image.getHeight() < 100)
                throw new ImageException("Image must be min a 100x100 resolution. Image is "
                        + image.getWidth() + "x" + image.getHeight());
        } catch (IOException ex) {
            throw new ImageException(ex.getMessage());
        }
    }

    private void expireEmployeesAvatars(Employee employee) {
        employee.getAvatars()
                .stream()
                .filter(avatar -> avatar.getIsExpired().equals(Boolean.FALSE))
                .forEach(avatar -> avatar.setIsExpired(Boolean.TRUE));
    }

    private void setOnlyActiveAddresses(Employee employee) {
        employee.setAddresses(employee.getAddresses()
                .stream()
                .filter(Address::getAddressHasActive)
                .collect(Collectors.toSet()));
    }

    private void setOnlyNotExpiredAvatars(Employee employee) {
        employee.setAvatars(employee.getAvatars()
                .stream()
                .filter(avatar ->
                        avatar.getIsExpired()
                                .equals(Boolean.FALSE))
                .collect(Collectors.toSet()));
    }

    @Override
    public Employee addWorkPassToEmployee(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        deletePassFromEmployee(id);
        employee.setWorkPass(passService.getFree());
        employee.getWorkPass().setIsFree(Boolean.FALSE);
        employee.getWorkPass().setExpireDate(LocalDateTime.now().plusYears(2));
        return employeeRepository.save(employee);
    }

    @Override
    public void deletePassFromEmployee(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (employee.getWorkPass() != null) {
            passService.removePass(employee.getWorkPass().getId());
            employee.setWorkPass(null);
        }
        employeeRepository.save(employee);
    }

}
