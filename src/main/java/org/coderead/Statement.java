package org.coderead;

import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * 客户服务类
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Statement {

    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = String.format("Statement for %s\n", invoice.getCustomer());
        StringBuilder stringBuilder = new StringBuilder(result);

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        for (Performance performance : invoice.getPerformances()) {

            volumeCredits += Math.max(performance.getAudience() - 30, 0);

            if ("comedy".equals(playFor(performance).getType())) {
                volumeCredits += Math.floor(performance.getAudience() / 5);
            }

            stringBuilder.append(String.format(" %s: %s (%d seats)\n", playFor(performance).getName(), format.format(amountFor(performance) /100), performance.getAudience()));
            totalAmount += amountFor(performance);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", format.format(totalAmount/100)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

    /**
     * 查询表演的剧目
     * @param perf 表演
     * @return 剧目
     */
    private Play playFor(Performance perf) {
        return plays.get(perf.getPlayId());
    }

    /**
     * 计算一场戏剧演出的费用
     * @param perf 表演
     * @return
     */
    private int amountFor(Performance perf) {
        Play play = playFor(perf);
        int result = 0;
        switch (play.getType()) {
            case "tragedy":
                result = 40000;
                if (perf.getAudience() > 30) {
                    result += 1000 * (perf.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (perf.getAudience() > 20) {
                    result += 10000 + 500 *(perf.getAudience() - 20);
                }
                result += 300 * perf.getAudience();
                break;
            default:
                throw new RuntimeException("unknown type:" + play.getType());
        }
        return result;
    }
}
