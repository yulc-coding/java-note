package org.ylc.note.shardingsphere.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 精确分表
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/7/24
 */
@Slf4j
public class LogPreciseShardingAlgorithm implements PreciseShardingAlgorithm<LocalDateTime> {

    /**
     * 表名分隔符
     */
    private static final String SEPARATOR = "_";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<LocalDateTime> shardingValue) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");

        String tableName = shardingValue.getLogicTableName();
        LocalDateTime time = shardingValue.getValue();

        String actTable = tableName + SEPARATOR + df.format(time);

        log.info("实际表：{}", actTable);
        return actTable;
    }

}
