package io.github.syske;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    public T mappingRow(ResultSet rs, int rownum) throws SQLException;
}
