package com.mlcandidate.davidguedez.foundproducts.presentation


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mlcandidate.davidguedez.R
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import com.mlcandidate.davidguedez.common.utils.setImage
import com.mlcandidate.davidguedez.databinding.RecyclerViewFoundProductBinding
import javax.inject.Inject

class FoundProductsAdapter @Inject constructor(val listenerCallback: (String) -> Unit) :
    ListAdapter<UIProduct, FoundProductsAdapter.FoundProductsVieWHolder>(ITEM_COMPARATOR) {

    companion object {
        const val EMPTY_VALUE = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundProductsVieWHolder {
        val binding = RecyclerViewFoundProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoundProductsVieWHolder(binding)
    }

    override fun onBindViewHolder(holder: FoundProductsVieWHolder, position: Int) {
        val item: UIProduct = getItem(position)
        val context = holder.itemView.context
        holder.bind(item, context)
        holder.itemView.setOnClickListener { listenerCallback(item.detailUrlLink) }
    }

    inner class FoundProductsVieWHolder(private val binding: RecyclerViewFoundProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UIProduct, context: Context) {
            with(binding) {
                foundProductTitle.text = item.title
                foundProductPrice.text = generatePriceText(item, context)
                foundProductInstallments.text = generateInstallmentText(item, context)
                foundProductArrival.text = generateShippingText(item, context)
                foundProductThumbnail.setImage(item.thumbnail)
            }

        }

        private fun generatePriceText(item: UIProduct, context: Context): CharSequence? {
            return "${context.getString(R.string.currency_symbol)} ${item.price}"
        }

        private fun generateShippingText(item: UIProduct, context: Context): CharSequence {
            return StringBuilder().apply {
                append(context.getString(R.string.shipping_arrives))
                append(context.getString(R.string.empty_space))
                if (item.productFreeShipping) {
                    append(context.getString(R.string.shipping_free))
                    append(context.getString(R.string.empty_space))
                }
                append(context.getString(R.string.shipping_arrives_tomorrow))
            }

        }

        private fun generateInstallmentText(
            installment: UIProduct,
            context: Context
        ): CharSequence {
            return if (installment.InstallmentAmount.isNotEmpty() && installment.installmentQuantity.isNotEmpty()) {
                "${context.getString(R.string.installment_in)} " +
                        "${installment.installmentQuantity} " +
                        "${context.getString(R.string.installment_multiplication)} " +
                        "${installment.InstallmentAmount} " +
                        context.getString(R.string.installment_without_fee)
            } else {
                EMPTY_VALUE
            }
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIProduct>() {
    override fun areItemsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem == newItem
    }
}