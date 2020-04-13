package com.ccg.btcremind;

import java.util.List;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-13 上午9:38
 */
public class RoomBean {

    /**
     * status : success
     * data : {"network":"ETH","prices":[{"price":"153.165","price_base":"USD","exchange":"coinbase","time":1586741824},{"price":"153.16","price_base":"USD","exchange":"gemini","time":1586741822},{"price":"153.14419357","price_base":"USD","exchange":"bitfinex","time":1586741806}]}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * network : ETH
         * prices : [{"price":"153.165","price_base":"USD","exchange":"coinbase","time":1586741824},{"price":"153.16","price_base":"USD","exchange":"gemini","time":1586741822},{"price":"153.14419357","price_base":"USD","exchange":"bitfinex","time":1586741806}]
         */

        private String network;
        private List<PricesBean> prices;

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public List<PricesBean> getPrices() {
            return prices;
        }

        public void setPrices(List<PricesBean> prices) {
            this.prices = prices;
        }

        public static class PricesBean {
            /**
             * price : 153.165
             * price_base : USD
             * exchange : coinbase
             * time : 1586741824
             */

            private String price;
            private String price_base;
            private String exchange;
            private int time;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPrice_base() {
                return price_base;
            }

            public void setPrice_base(String price_base) {
                this.price_base = price_base;
            }

            public String getExchange() {
                return exchange;
            }

            public void setExchange(String exchange) {
                this.exchange = exchange;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            @Override
            public String toString() {
                return "PricesBean{" +
                        "price='" + price + '\'' +
                        ", price_base='" + price_base + '\'' +
                        ", exchange='" + exchange + '\'' +
                        ", time=" + time +
                        '}';
            }
        }
    }
}
