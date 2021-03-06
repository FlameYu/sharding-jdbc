/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingjdbc.core.merger;

import io.shardingjdbc.core.merger.show.ShowDatabasesResultSetMerger;
import io.shardingjdbc.core.merger.show.ShowOtherResultSetMerger;
import io.shardingjdbc.core.merger.show.ShowTablesResultSetMerger;
import io.shardingjdbc.core.parsing.parser.dialect.mysql.statement.ShowStatement;
import io.shardingjdbc.core.parsing.parser.dialect.mysql.statement.ShowType;
import io.shardingjdbc.core.rule.ShardingRule;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Show result set merge engine.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
public final class ShowMergeEngine implements MergeEngine {
    
    private final ShardingRule shardingRule;
    
    private final List<ResultSet> resultSets;
    
    private final ShowStatement showStatement;
    
    @Override
    public ResultSetMerger merge() throws SQLException {
        if (ShowType.DATABASES == showStatement.getShowType()) {
            return new ShowDatabasesResultSetMerger();
        }
        if (ShowType.TABLES == showStatement.getShowType()) {
            return new ShowTablesResultSetMerger(shardingRule, resultSets);
        }
        return new ShowOtherResultSetMerger(resultSets.get(0));
    }
}
