package com.uk.christo.ciderKit.domain.model

data class SugarCalculationInput(
    val startingSG: Float,
    val targetSG: Float,
    val volume: Float,
    val isImperial: Boolean = false
)

data class SugarCalculationResult(
    val sugarToAdd: Float
)

data class AlcoholCalculationInput(
    val originalGravity: Float,
    val targetGravity: Float
)

data class AlcoholCalculationResult(
    val alcoholPercentage: Float
)

object BrewingCalculations {
    
    fun calculateSugarToAdd(input: SugarCalculationInput): SugarCalculationResult {
        var volume = input.volume
        
        // Convert volume from pints to liters if imperial
        if (input.isImperial) {
            volume /= 1.7598f
        }
        
        // Calculate Brix values using the formula from original code
        val startBrix = calculateBrix(input.startingSG)
        val endBrix = calculateBrix(input.targetSG)
        
        var sugarToAdd = volume * input.startingSG * (endBrix - startBrix) / (100 - endBrix)
        
        // Convert to pounds if imperial
        if (input.isImperial) {
            sugarToAdd *= 2.20462f
        }
        
        return SugarCalculationResult(sugarToAdd)
    }
    
    fun calculateAlcoholPercentage(input: AlcoholCalculationInput): AlcoholCalculationResult {
        val alcoholPercentage = ((1.05f * (input.originalGravity - input.targetGravity)) / 0.79f) * 100f
        return AlcoholCalculationResult(alcoholPercentage)
    }
    
    private fun calculateBrix(specificGravity: Float): Float {
        return ((182.4601f * specificGravity - 775.6831f) * specificGravity + 1262.7794f) * specificGravity - 669.5622f
    }
}