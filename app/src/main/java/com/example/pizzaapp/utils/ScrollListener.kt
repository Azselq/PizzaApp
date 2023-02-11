package com.example.pizzaapp.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    context: Context,
    loadNextPage: () -> Unit,
    private val pageSize: Int = DEFAULT_PAGE_SIZE
): RecyclerView.OnScrollListener(),
    View.OnTouchListener,
    GestureDetector.OnGestureListener {


    companion object {
        /**
         * Размер страницы для пагинации по-умолчанию.
         */
        const val DEFAULT_PAGE_SIZE = 10
    }

    private var gestureDetector: GestureDetectorCompat? = GestureDetectorCompat(context, this@ScrollListener)

    /**
     * Предыдущее событие скролла. Больше 0 - скролл вниз, меньше нуля - скролл вверх.
     */
    private var lastVerticalScrollDelta = 0F

    /**
     * true если последний элемент [RecyclerView] показан, иначе false.
     */
    private var lastIsShown = false

    /**
     * Должен заружать новую страницу для пагинации.
     */
    private var loadNextPage: (() -> Unit)? = loadNextPage

    /**
     * Освобождает ресурсы.
     */
    fun release() {
        gestureDetector = null
        loadNextPage = null
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (p1 != null) {
            gestureDetector?.onTouchEvent(p1)
        }
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) = Unit

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        lastVerticalScrollDelta = distanceY
        return false
    }

    override fun onLongPress(p0: MotionEvent?) = Unit

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val capturedLoadNextPage = loadNextPage ?: return
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        if (dy > 0 && layoutManager.isNeedLoadNextPage()) {
            capturedLoadNextPage()
        }
        lastIsShown = layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val capturedLoadNextPage = loadNextPage ?: return
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_SETTLING
            && lastIsShown
            && lastVerticalScrollDelta > 0F) {
            capturedLoadNextPage()
        }
    }

    private fun LinearLayoutManager.isNeedLoadNextPage(): Boolean =
        findLastVisibleItemPosition() >= itemCount - 1 - pageSize / 2


}