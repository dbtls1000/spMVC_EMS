package com.biz.ems.model;

public class Pager {
	public static final int PAGE_SCALE = 10; // 페이지당 게시물 수
	public static final int BLOCK_SCALE = 10; // 화면당 페이지수

	private int curPage; // 현재 페이지
	private int prevPage; // 이전 페이지
	private int nextPage; // 다음 페이지
	private int totalPage; // 전체 페이지 개수
	private int totalBlock; // 전체 페이지 블록개수
	private int curBlock; // 현재블록
	private int prevBlock; // 이전블록
	private int nextBlock; // 다음블록
	private int pageBegin; // #{start}값
	private int pageEnd; // #{end}값
	private int blockBegin; // 블록의 시작페이지 번호
	private int blockEnd; // 블록의 끝페이지 번호

	// Pager(레코드개수,출력할페이지번호)
	public Pager(int count, int curPage) {
		curBlock = 1; // 현재블록번호
		this.curPage = curPage; // 현재 페이지 번호
		setTotalPage(count); // 전체 페이지 개수 계산
		setPageRange(); // #{start}#{end}값 계산
		setTotalBlock(); // 전체 블록 개수 계산
		setBlockRange(); // 블록의 시작,끝 번호 계산
	}

	public void setPageRange() {
		// 시작번호 = (현재페이지-1)x페이지당 게시물수 +1
		// 끝번호 = 시작번호 +페이지당 게시물수 -1
		pageBegin = (curPage - 1) * PAGE_SCALE + 1;
		pageEnd = pageBegin + PAGE_SCALE - 1;
	}
	public void setTotalBlock() {
		totalBlock = (int)Math.ceil(totalPage*1.0/BLOCK_SCALE);
	}
	// 블록의 개수 계산
	public void setBlockRange() {
		// 원하는페이지가 몇번째 블록에 속하는지 계산
		curBlock = (curPage - 1) / BLOCK_SCALE + 1;
		// 블록의 시작페이지 끝페이지 번호 계산
		blockBegin = (curBlock - 1) * BLOCK_SCALE + 1;
		blockEnd = blockBegin + BLOCK_SCALE - 1;
		// 마지막블록 번호가 번위를 초과하지 않도록 처리
		if (blockEnd > totalPage) {
			blockEnd = totalPage;
		}
		// [이전] [다음]을 눌럿을때 이동할 페이지 번호
		prevPage = (curBlock == 1) ? 1 : (curBlock - 1) * BLOCK_SCALE;
		nextPage = curBlock > totalBlock ? (curBlock * BLOCK_SCALE) : (curBlock * BLOCK_SCALE) + 1;
		// 마지막 페이지가 범위를 초과하지 않도록 처리
		if(nextPage >= totalPage) {
			nextPage = totalPage;
		}

	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int count) {
		//Math.ceil()올림
		totalPage = (int)Math.ceil(count*1.0/PAGE_SCALE);
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getPrevBlock() {
		return prevBlock;
	}

	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}

	public int getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}

	public int getPageBegin() {
		return pageBegin;
	}

	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getBlockBegin() {
		return blockBegin;
	}

	public void setBlockBegin(int blockBegin) {
		this.blockBegin = blockBegin;
	}

	public int getBlockEnd() {
		return blockEnd;
	}

	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}

	public static int getPageScale() {
		return PAGE_SCALE;
	}

	public static int getBlockScale() {
		return BLOCK_SCALE;
	}

	@Override
	public String toString() {
		return "Pager [curPage=" + curPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + ", totalPage="
				+ totalPage + ", totalBlock=" + totalBlock + ", curBlock=" + curBlock + ", prevBlock=" + prevBlock
				+ ", nextBlock=" + nextBlock + ", pageBegin=" + pageBegin + ", pageEnd=" + pageEnd + ", blockBegin="
				+ blockBegin + ", blockEnd=" + blockEnd + "]";
	}
}
