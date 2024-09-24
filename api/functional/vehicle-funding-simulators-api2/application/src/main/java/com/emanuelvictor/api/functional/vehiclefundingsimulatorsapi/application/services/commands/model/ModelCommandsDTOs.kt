package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.model

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandOutput

@JvmRecord
data class ModelCommandInput(val name: String, val brand: BrandCommandInput)

@JvmRecord
data class ModelCommandOutput(val name: String, val brand: BrandCommandOutput)
