package covid.tracing.tracing;

public class Pagination {

    private int pageSize;

    private int currentPageIndex;

    private int totalCnt;

    private int totalPageIndex;

    public Pagination(int currentPageIndex) {
        this.pageSize = 35; // default size
        this.currentPageIndex = currentPageIndex;
    }

    public Pagination(int currentPageIndex, int pageSize) {
        this.pageSize = pageSize;
        this.currentPageIndex = currentPageIndex;
    }

    public void setTotalCnt(int totalDataCnt) {
        this.totalCnt = totalDataCnt;
    }

    public int getTotalCnt() {
        return this.totalCnt;
    }

    public int calAndGetTotalPageIndex() {
        if(this.totalCnt == 0) {
            this.totalPageIndex = 1;
        } else {
            this.totalPageIndex = this.totalCnt / this.pageSize;
            if(this.totalCnt % this.pageSize != 0) {
                this.totalPageIndex += 1;
            }
        }
        return this.totalPageIndex;
    }

    public int getLimit() {
        return this.pageSize;
    }

    public int getOffset() {
        return (this.currentPageIndex - 1) * 35;
    }
}
