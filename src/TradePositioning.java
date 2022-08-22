import java.util.Set;
/**
 * POJO class to hold the final result records
 * This class is implemented with Builder design pattern.
 *
 */
public class TradePositioning {
    public Set<Integer> trades;
    public String instrument;
    public int tradeQuantity;
    public String accountId;

    public TradePositioning(TradePositioningBuilder builder){
        this.accountId = builder.accountId;
        this.instrument = builder.instrument;
        this.trades = builder.trades;
        this.tradeQuantity = builder.tradeQuantity;
    }

    @Override
    public String toString() {
        return "TradePositioning{" +
                "trades=" + trades +
                ", instrument='" + instrument + '\'' +
                ", tradeQuantity=" + tradeQuantity +
                ", accountId='" + accountId + '\'' +
                '}'+ '\n';
    }

    public static class TradePositioningBuilder{
        private Set<Integer> trades;
        private String instrument;
        private int tradeQuantity;
        private String accountId;

        public TradePositioningBuilder trades (Set<Integer> tradeList){
            this.trades = tradeList;
            return this;
        }
        public TradePositioningBuilder instrument (String instrument){
            this.instrument = instrument;
            return this;
        }
        public TradePositioningBuilder tradeQuantity (int tradeQuantity){
            this.tradeQuantity = tradeQuantity;
            return this;
        }
        public TradePositioningBuilder accountId (String accountId){
            this.accountId = accountId;
            return this;
        }
        public TradePositioning build(){
            TradePositioning tradePositioning = new TradePositioning(this);
            return tradePositioning;
        }
    }
}
