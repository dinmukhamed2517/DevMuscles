package kz.just_code.devmuscles.utilities

import kz.just_code.devmuscles.firebase.Goal

fun calculateDailyCalories(
    currentWeight: Double?,
    goalWeight: Double?,
    age: Int?,
    height: Double?,
    goal: Goal?
): Double {
    val baseCalories: Double = 10.0
    val weightFactor: Double = 6.25
    val heightFactor: Double = 5.0
    val ageFactor: Double = 5.0

    val bmr = baseCalories +
            (weightFactor * (currentWeight ?: 0.0)) +
            (heightFactor * (height ?: 0.0)) -
            (ageFactor * (age ?: 0))

    val calorieAdjustment = when (goal) {
        Goal.LOSE_WEIGHT -> -500.0
        Goal.GAIN_MUSCLES -> 500.0
        Goal.GET_FITTER -> 300.0
        else -> 0.0
    }

    val dailyCalories = (bmr + calorieAdjustment)* goalWeight!! / currentWeight!!

    return dailyCalories
}
