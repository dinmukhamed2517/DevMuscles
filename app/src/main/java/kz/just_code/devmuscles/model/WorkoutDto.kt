package kz.just_code.devmuscles.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kz.just_code.devmuscles.Type

@Parcelize

data class WorkoutDto(
    var id:Int,
    var title:String,
    var type: Type,
    var calories:Int,
    var duration:Int,
    var description:String,
):Parcelable
