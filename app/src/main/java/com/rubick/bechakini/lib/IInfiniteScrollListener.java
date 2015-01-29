package com.rubick.bechakini.lib;

public interface IInfiniteScrollListener {
    public void endIsNear();

    // Item visibility code
    public void onScrollCalled(int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
