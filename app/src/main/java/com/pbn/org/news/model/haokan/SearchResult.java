package com.pbn.org.news.model.haokan;

import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/31
 */
public class SearchResult {

    private Search search;

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public class Search{
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }
    public class Data{
        private int has_more;
        private List<SearchVideo> list;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<SearchVideo> getList() {
            return list;
        }

        public void setList(List<SearchVideo> list) {
            this.list = list;
        }
    }
}
