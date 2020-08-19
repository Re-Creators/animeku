package com.richardo.animeku

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(var spanCount : Int, var spacing: Int, var includeEdge : Boolean)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position : Int = parent.getChildAdapterPosition(view)
        val coloumn : Int = position % spanCount

        if(includeEdge){
            outRect.left = spacing - coloumn * spacing / spanCount
            outRect.right = (coloumn + 1) * spacing / spanCount

            if(position < spanCount){
                outRect.top = spacing
            }
            outRect.bottom = spacing
        }else {
            outRect.left = coloumn * spacing / spanCount// column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (coloumn + 1) * spacing / spanCount// spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing// item top
            }
        }
    }
}