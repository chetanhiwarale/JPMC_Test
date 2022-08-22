/**
 * POJO class to hold the input Trade Event records
 * This class is implemented with Builder design pattern.
 *
 */
public class TradeEvent {
    private int tradeId;
    private int version;
    private String secIdentifier;
    private int tradeQuantity;
    private String tradeDirection;
    private String accountId;
    private String operation;

    private TradeEvent(TradeEventBuilder tradeEventBuilder){
        this.tradeId = tradeEventBuilder.tradeId;
        this.version = tradeEventBuilder.version;
        this.secIdentifier = tradeEventBuilder.secIdentifier;
        this.tradeQuantity = tradeEventBuilder.tradeQuantity;
        this.tradeDirection = tradeEventBuilder.tradeDirection;
        this.accountId = tradeEventBuilder.accountId;
        this.operation = tradeEventBuilder.operation;
    }

    public int getTradeId() {
        return tradeId;
    }
    public int getVersion() {
        return version;
    }
    public String getSecIdentifier() {
        return secIdentifier;
    }
    public int getTradeQuantity() {
        return tradeQuantity;
    }
    public String getTradeDirection() {
        return tradeDirection;
    }
    public String getAccountId() {
        return accountId;
    }
    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "TradeEvent{" +
                "tradeId=" + tradeId +
                ", version=" + version +
                ", secIdentifier='" + secIdentifier + '\'' +
                ", tradeQuantity=" + tradeQuantity +
                ", tradeDirection='" + tradeDirection + '\'' +
                ", accountId='" + accountId + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }

    public static class TradeEventBuilder{
        private int tradeId;
        private int version;
        private String secIdentifier;
        private int tradeQuantity;
        private String tradeDirection;
        private String accountId;
        private String operation;

        public TradeEventBuilder tradeId(int tradeId){
            this.tradeId = tradeId;
            return this;
        }

        public TradeEventBuilder version(int version){
            this.version = version;
            return this;
        }

        public TradeEventBuilder secIdentifier(String secIdentifier){
            this.secIdentifier = secIdentifier;
            return this;
        }

        public TradeEventBuilder tradeQuantity(int tradeQuantity){
            this.tradeQuantity = tradeQuantity;
            return this;
        }

        public TradeEventBuilder tradeDirection(String tradeDirection){
            this.tradeDirection = tradeDirection;
            return this;
        }
        public TradeEventBuilder accountId(String accountId){
            this.accountId = accountId;
            return this;
        }
        public TradeEventBuilder operation(String operation){
            this.operation = operation;
            return this;
        }

        public TradeEvent build(){
            TradeEvent tradeEvent = new TradeEvent(this);
            return tradeEvent;
        }

    }
}
