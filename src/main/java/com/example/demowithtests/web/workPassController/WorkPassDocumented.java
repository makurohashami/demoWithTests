package com.example.demowithtests.web.workPassController;

import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import com.example.demowithtests.util.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "WorkPass", description = "Employee API")
public interface WorkPassDocumented extends WorkPassController {

    @Override
    @Operation(summary = "This is endpoint to add a new work pass.", description = "Create request to add a new employee.", tags = {"WorkPass"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    WorkPassResponse createPass(WorkPassRequest request);

    @Override
    @Operation(summary = "This is endpoint returned a work pass by his id.", description = "Create request to read a work pass by id", tags = {"WorkPass"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified work pass request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    WorkPassResponse readPass(Integer id);

    @Override
    @Operation(summary = "This is endpoint to update a work pass by his id.", description = "Create request to update a work pass by id", tags = {"WorkPass"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified work pass request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    WorkPassResponse updatePass(Integer id, WorkPassRequest request);

    @Override
    @Operation(summary = "This is endpoint to remove a work pass by his id.", description = "Create request to remove a work pass by id", tags = {"WorkPass"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK. Employee deleted."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified work pass request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void deletePass(Integer id);
}
