import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Main class which process input trade data and calculate final trade positioning
 * for unique combination of account id and security identifier
 *
 */
public class TradePositioningProgram {

    public List<TradePositioning> processTradePositioning(List<TradeEvent> inputTradeEventsList) {

        //filter data by unique combination of Account Id and Security identifier
        List<TradeEvent> tradeEvents = inputTradeEventsList.stream()
                .filter(distinctByKeys(TradeEvent::getAccountId, TradeEvent::getSecIdentifier))
                .collect(Collectors.toList());

        List<TradePositioning> tradePositioningList = new ArrayList<>();

        calculateFinalPositioning(tradeEvents, tradePositioningList, inputTradeEventsList);

        System.out.println("Final Positioning Output : " +"\n"+ tradePositioningList);
        return tradePositioningList;
    }

    /**
     * Method to filter out records based on unique combination of account is and security identifier
     *
     * @param keyExtractors : list of parameters for unique key combination
     * @return return predicate
     */
    public <T> Predicate<T>
    distinctByKeys(final Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    /**
     * Method to calculate final positioning based on business rules
     *
     * @param tradeEvents : list of records with unique key combinations
     * @param outputTradePositioningList : list of output records
     * @param inputTradeEventsList : list of input records
     */
    public void calculateFinalPositioning(List<TradeEvent> tradeEvents,
                                                 List<TradePositioning> outputTradePositioningList,
                                                 List<TradeEvent> inputTradeEventsList){
        for(TradeEvent c : tradeEvents ){
            List<TradeEvent> tradeEventList1 = inputTradeEventsList.stream()
                    .filter(p -> p.getAccountId().equals(c.getAccountId())
                            && p.getSecIdentifier().equals(c.getSecIdentifier())).collect(Collectors.toList());

            int finalPositioning = 0;
            Set<Integer> tradeIds = new HashSet<>();
            for(TradeEvent t : tradeEventList1){
                tradeIds.add(t.getTradeId());
                if(t.getTradeDirection().equals(Direction.BUY.name())
                        && (t.getOperation().equals(Operation.NEW.name()) || t.getOperation().equals(Operation.AMEND.name()))){
                    finalPositioning = finalPositioning + t.getTradeQuantity();
                } else if (t.getTradeDirection().equals(Direction.BUY)
                        && t.getOperation().equals(Operation.CANCEL)) {
                    finalPositioning = finalPositioning - t.getTradeQuantity();;
                } else if (t.getTradeDirection().equals(Direction.SELL)
                        && (t.getOperation().equals(Operation.CANCEL))) {
                    finalPositioning = finalPositioning + t.getTradeQuantity();
                } else if (t.getTradeDirection().equals(Direction.SELL) && (t.getOperation().equals(Operation.NEW)
                        || t.getOperation().equals(Operation.AMEND))) {
                    finalPositioning = finalPositioning - t.getTradeQuantity();
                }
            };
            TradePositioning tradePositioning = new TradePositioning.TradePositioningBuilder()
                    .instrument(c.getSecIdentifier()).accountId(c.getAccountId())
                    .trades(tradeIds).tradeQuantity(finalPositioning).build();

            outputTradePositioningList.add(tradePositioning);
        };
    }
}
