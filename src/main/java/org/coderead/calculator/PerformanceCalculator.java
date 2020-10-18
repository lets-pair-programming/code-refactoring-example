package org.coderead.calculator;

import org.coderead.model.Performance;
import org.coderead.model.Play;

/**
 * 演出计算器
 *
 * @author kendoziyu
 * @since 2020/10/18 0018
 */
public class PerformanceCalculator {

    protected Performance performance;
    protected Play play;


    public PerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public Play play() {
        return play;
    }

    /**
     * 计算一场戏剧演出的费用
     * @return
     */
    public int amount() {
        throw new RuntimeException("subclass responsibility");
    }

    /**
     * 计算观众量积分
     * @return 观众量积分
     */
    public int volumeCredits() {
        int result = 0;
        result += Math.max(performance.getAudience() - 30, 0);

        if ("comedy".equals(play.getType())) {
            result += Math.floor(performance.getAudience() / 5);
        }
        return result;
    }
}
