package cgg.gov.`in`.githubtask.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cgg.gov.`in`.githubtask.R
import cgg.gov.`in`.githubtask.databinding.ItemRowBinding
import cgg.gov.`in`.githubtask.model.ItemsRes


class MyRecyclerViewAdapter(dataModelList: List<ItemsRes>, ctx: Context) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val dataModelList: List<ItemsRes>
    private val context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_row, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: ItemsRes = dataModelList[position]
        holder.bind(dataModel)
        holder.itemRowBinding.root.setOnClickListener {
            dataModel.htmlUrl.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                it.context.startActivity(intent)
            }
        }
        holder.itemRowBinding.tvName.setText(dataModel.fullName)
        holder.itemRowBinding.tvDes.setText(dataModel.description)

    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    inner class ViewHolder(itemRowBinding: ItemRowBinding) :
        RecyclerView.ViewHolder(itemRowBinding.getRoot()) {
        var itemRowBinding: ItemRowBinding
        fun bind(obj: Any?) {
            itemRowBinding.executePendingBindings()
        }

        init {
            this.itemRowBinding = itemRowBinding
        }
    }

    init {
        this.dataModelList = dataModelList
        context = ctx
    }
}