package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.vehicle

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.model.ModelRestOutput

@JvmRecord
data class VehicleRestInput(val plateNumber: String, val model: ModelRestInput)

@JvmRecord
data class VehicleRestOutput(val plateNumber: String, val model: ModelRestOutput)