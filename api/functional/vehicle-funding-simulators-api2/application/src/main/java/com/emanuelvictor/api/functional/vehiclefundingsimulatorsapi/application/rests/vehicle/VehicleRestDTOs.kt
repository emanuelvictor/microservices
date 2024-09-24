package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.model.ModelRestOutput

@JvmRecord
data class VehicleRestInput(val plateNumber: String, val model: ModelRestInput)

@JvmRecord
data class VehicleRestOutput(val plateNumber: String, val model: ModelRestOutput)