package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.dto.cabinet.CabinetRequest;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import com.example.demowithtests.util.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cabinet", description = "Employee API")
public interface CabinetDocumented extends CabinetController {

    @Override
    @Operation(summary = "This is endpoint to add a new cabinet.", description = "Create request to add a new cabinet.", tags = {"Cabinet"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new cabinet is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    CabinetResponse createCabinet(CabinetRequest request);

    @Override
    @Operation(summary = "This is endpoint returned a cabinet by his id.", description = "Create request to read a cabinet by id", tags = {"Cabinet"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified cabinet request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    CabinetResponse readCabinet(Integer id);

    @Override
    @Operation(summary = "This is endpoint to update a cabinet by his id.", description = "Create request to update a cabinet by id", tags = {"Cabinet"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified cabinet request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    CabinetResponse updateCabinet(Integer id, CabinetRequest request);

    @Override
    @Operation(summary = "This is endpoint to remove a cabinet by his id.", description = "Create request to remove a cabinet by id", tags = {"Cabinet"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK. Cabinet deleted."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified cabinet request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void deleteCabinet(Integer id);
}
