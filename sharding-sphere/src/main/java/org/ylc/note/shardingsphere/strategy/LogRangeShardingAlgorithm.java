package org.ylc.note.shardingsphere.strategy;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 范围查询策略
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/7/24
 */
@Slf4j
public class LogRangeShardingAlgorithm implements RangeShardingAlgorithm<LocalDateTime> {

    /**
     * 表名分隔符
     */
    private static final String SEPARATOR = "_";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<LocalDateTime> shardingValue) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<String> tableSet = new HashSet<>();
        String tableName = shardingValue.getLogicTableName();
        Range<LocalDateTime> dates = shardingValue.getValueRange();

        // 获取时间范围段
        LocalDateTime lowerTime = dates.lowerEndpoint();
        LocalDateTime upperTime = dates.upperEndpoint();

        while (lowerTime.isBefore(upperTime)) {
            tableSet.add(tableName + SEPARATOR + df.format(lowerTime));
            lowerTime = lowerTime.plusMonths(1);
        }
        if (lowerTime.getMonthValue() == upperTime.getMonthValue()) {
            tableSet.add(tableName + SEPARATOR + df.format(lowerTime));
        }
        log.info("查询表集合：{}", tableSet);

        return tableSet;
    }

}
