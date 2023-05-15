package kg.kunduznbkva.newsapp.utils

import androidx.recyclerview.widget.DiffUtil
import kg.kunduznbkva.newsapp.model.Article

class NewsDiffUtils(
    private val oldList: List<Article>,
    private val newList: List<Article>
) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
