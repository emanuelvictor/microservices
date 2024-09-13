package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.brand

@JvmRecord
data class BrandRestInput(val name: String)

@JvmRecord
data class BrandRestOutput(val name: String)