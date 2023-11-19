package kz.just_code.devmuscles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.base.BaseMessageViewHolder
import kz.just_code.devmuscles.base.BaseWorkoutViewHolder
import kz.just_code.devmuscles.databinding.ItemMessageBinding
import kz.just_code.devmuscles.repository.gpt.model.Choice
import kz.just_code.devmuscles.repository.gpt.model.Message
import java.text.SimpleDateFormat
import java.util.Date

class ItemMessageAdapter:ListAdapter<Choice, BaseMessageViewHolder<*>>(MessageDiffUtils()) {

    class MessageDiffUtils:DiffUtil.ItemCallback<Choice>(){
        override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem.index == newItem.index
        }

        override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<*> {
        return MessageViewHolder(
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseMessageViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))

    }

    inner class MessageViewHolder(binding: ItemMessageBinding):BaseMessageViewHolder<ItemMessageBinding>(binding){
        override fun bindView(item: Choice) {
            with(binding){
                content.text = item.message.content
                time.text = getCurrentHourAndMinute()
            }
        }

    }
}


fun getCurrentHourAndMinute(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val date = Date(currentTimeMillis)

    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(date)
}