import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradePositioningProgramTest {

    public TradePositioningProgram tradePositioningProgram;
    List<TradeEvent> tradeEventList = new ArrayList<>();

    @BeforeAll
    public void setup() {

        tradePositioningProgram = new TradePositioningProgram();

        TradeEvent tradeEvent1 =  new TradeEvent.TradeEventBuilder().tradeId(1234)
                .version(1).secIdentifier("XYZ").tradeQuantity(100).tradeDirection("BUY")
                .accountId("ACC-1234").operation("NEW").build();
        TradeEvent tradeEvent2 =  new TradeEvent.TradeEventBuilder().tradeId(1234)
                .version(2).secIdentifier("XYZ").tradeQuantity(150).tradeDirection("BUY")
                .accountId("ACC-1234").operation("AMEND").build();
        TradeEvent tradeEvent3 =  new TradeEvent.TradeEventBuilder().tradeId(5678)
                .version(1).secIdentifier("QED").tradeQuantity(200).tradeDirection("BUY")
                .accountId("ACC-2345").operation("NEW").build();
        TradeEvent tradeEvent4 =  new TradeEvent.TradeEventBuilder().tradeId(5678)
                .version(2).secIdentifier("QED").tradeQuantity(0).tradeDirection("BUY")
                .accountId("ACC-2345").operation("CANCEL").build();
        TradeEvent tradeEvent5 =  new TradeEvent.TradeEventBuilder().tradeId(2233)
                .version(1).secIdentifier("RET").tradeQuantity(100).tradeDirection("SELL")
                .accountId("ACC-3456").operation("NEW").build();
        TradeEvent tradeEvent6 =  new TradeEvent.TradeEventBuilder().tradeId(2244)
                .version(2).secIdentifier("RET").tradeQuantity(400).tradeDirection("SELL")
                .accountId("ACC-3456").operation("AMEND").build();
        TradeEvent tradeEvent7 =  new TradeEvent.TradeEventBuilder().tradeId(2233)
                .version(3).secIdentifier("RET").tradeQuantity(0).tradeDirection("SELL")
                .accountId("ACC-3456").operation("CANCEL").build();
        TradeEvent tradeEvent8 =  new TradeEvent.TradeEventBuilder().tradeId(2233)
                .version(3).secIdentifier("BMW").tradeQuantity(0).tradeDirection("SELL")
                .accountId("ACC-3456").operation("CANCEL").build();

        tradeEventList.add(tradeEvent1);
        tradeEventList.add(tradeEvent2);
        tradeEventList.add(tradeEvent3);
        tradeEventList.add(tradeEvent4);
        tradeEventList.add(tradeEvent5);
        tradeEventList.add(tradeEvent6);
        tradeEventList.add(tradeEvent7);
        tradeEventList.add(tradeEvent8);
    }

    @DisplayName("Main program output test")
    @Test
    void testProcessTradePositioning() {
        List<TradePositioning> tradePositioningList = tradePositioningProgram.processTradePositioning(tradeEventList);
        assertEquals(4, tradePositioningList.size());
    }

    @DisplayName("test to calculate final positioning amount")
    @Test
    void testCalculateFinalPositioning() {
        List<TradeEvent> list = new ArrayList<>();
        list.add(new TradeEvent.TradeEventBuilder().accountId("ACC-1234").secIdentifier("XYZ").build());
        list.add(new TradeEvent.TradeEventBuilder().accountId("ACC-2345").secIdentifier("QED").build());
        list.add(new TradeEvent.TradeEventBuilder().accountId("ACC-3456").secIdentifier("RET").build());
        list.add(new TradeEvent.TradeEventBuilder().accountId("ACC-3456").secIdentifier("BMW").build());
        List<TradePositioning> outputTradePositioningList = new ArrayList<>();

        tradePositioningProgram.calculateFinalPositioning(list,
                outputTradePositioningList, tradeEventList);
        assertEquals(4, outputTradePositioningList.size());
        assertEquals(1, outputTradePositioningList.stream().filter(t -> {
            return t.trades.size() == 2;
        }).collect(Collectors.toList()).size());
        assertEquals(2, outputTradePositioningList.stream()
                .filter(t -> t.accountId.equals("ACC-3456")).collect(Collectors.toList()).size());
    }
}