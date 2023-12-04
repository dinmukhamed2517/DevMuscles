package kz.just_code.devmuscles.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.just_code.devmuscles.base.BaseAdViewHolder
import kz.just_code.devmuscles.databinding.ItemAdvertisementBinding
import kz.just_code.devmuscles.utilities.AdDao

class AdAdapter:ListAdapter<AdDao, BaseAdViewHolder<*>>(AdDiffUtils()) {

    class AdDiffUtils:DiffUtil.ItemCallback<AdDao>(){
        override fun areItemsTheSame(oldItem: AdDao, newItem: AdDao): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AdDao, newItem: AdDao): Boolean {
            return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdViewHolder<*> {
        return AdViewHolder(
            ItemAdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseAdViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class AdViewHolder(binding:ItemAdvertisementBinding):BaseAdViewHolder<ItemAdvertisementBinding>(binding){
        override fun bindView(item: AdDao) {
            with(binding){
                mainImage.setImageResource(item.url)
                title.text = item.title
                chipText.text = item.description
            }
        }
    }
}


