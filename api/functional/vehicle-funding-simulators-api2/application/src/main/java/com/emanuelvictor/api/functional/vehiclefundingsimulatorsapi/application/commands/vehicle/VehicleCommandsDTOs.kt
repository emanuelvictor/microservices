package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.vehicle

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.model.ModelCommandOutput

@JvmRecord
data class VehicleCommandInput(val plateNumber: String, val model: ModelCommandInput)

@JvmRecord
data class VehicleCommandOutput(val plateNumber: String, val model: ModelCommandOutput)