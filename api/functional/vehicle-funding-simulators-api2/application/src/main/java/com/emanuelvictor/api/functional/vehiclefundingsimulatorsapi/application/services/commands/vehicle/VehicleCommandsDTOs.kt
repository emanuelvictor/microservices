package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.vehicle

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model.ModelCommandOutput

@JvmRecord
data class VehicleCommandInput(val plateNumber: String, val model: ModelCommandInput)

@JvmRecord
data class VehicleCommandOutput(val plateNumber: String, val model: ModelCommandOutput)