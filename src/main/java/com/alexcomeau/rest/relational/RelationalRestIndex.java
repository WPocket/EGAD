package com.alexcomeau.rest.relational;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.alexcomeau.Main;
import com.alexcomeau.database.DatabaseException;
import com.alexcomeau.rest.RestError;
import com.alexcomeau.rest.relational.objects.EntryPair;
import com.alexcomeau.rest.relational.objects.RelationalReturn;
import com.alexcomeau.rest.relational.objects.datatypes.StringData;
import com.alexcomeau.rest.relational.objects.datatypes.ListString;
import com.alexcomeau.rest.relational.objects.datatypes.ListTable;
import com.alexcomeau.rest.relational.objects.datatypes.ListPair;
import com.alexcomeau.rest.relational.objects.datatypes.Table;
import com.alexcomeau.utils.Common;
import com.alexcomeau.utils.ResponseCode;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("rel/{index}/")
/**
 * KVRest
 */
public class RelationalRestIndex {
    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column column.{column} in the first row")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })

    @GetMapping("/select/table.{table}/column.{column}")
    public Serializable getKey(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column) {
        String res;
        try {
            res = Main.relational.get(index).select(column, table);
        } catch (DatabaseException e) {

            response.setStatus(ResponseCode.BAD_REQUEST.code);

            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<String>().setData(res);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column column.{column} in the first row with the sql option/s where.{where}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/column.{column}/where.{where}")
    public Serializable getKeyWhere(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where) {
        String res;
        try {
            res = Main.relational.get(index).selectWhere(column, table, where);
        } catch (DatabaseException e) {

            response.setStatus(ResponseCode.BAD_REQUEST.code);

            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<String>().setData(res);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column column.{column} in the row in position offset, if offset does not exist then it returns nothing ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/column.{column}/offset.{offset}")
    public Serializable getKeyOffset(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset) {
        String res;
        try {
            res = Main.relational.get(index).selectOffset(column, table, Integer.parseInt(offset));
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {

            response.setStatus(ResponseCode.BAD_REQUEST.code);

            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<String>().setData(res);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column column.{column} in the row in position offset {offset} with the sql option/s where.{where}, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StringData.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/column.{column}/offset.{offset}/where.{where}")
    public Serializable getKeyOffsetWhere(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where) {
        String res;
        try {
            res = Main.relational.get(index).selectWhereOffset(column, table, where, Integer.parseInt(offset));
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {

            response.setStatus(ResponseCode.BAD_REQUEST.code);

            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<String>().setData(res);
    }
    // @Operation(summary = "from database at index {index}, select from table
    // table.{table} the value in multiple columns {columns}(comma separated) in the
    // row in position offset {offset} with the sql option/s where.{where}, if
    // offset does not exist then it returns nothing")

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in multiple columns {columns}(comma separated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/columns.{columns}")
    public Serializable getMultiple(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns) {
        HashMap<String, String> hmap = new HashMap<>();
        ArrayList<String> columnAList = new ArrayList<>();
        for (String s : columns) {
            columnAList.add(s);
        }
        try {
            hmap = Main.relational.get(index).selectMultiple(columnAList, table);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<HashMap<String, String>>().setData(hmap);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in multiple columns {columns}(comma separated)with the sql option/s where.{where}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/columns.{columns}/where.{where}")
    public Serializable getMultipleWhere(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where) {
        HashMap<String, String> hmap = new HashMap<>();
        ArrayList<String> columnAList = new ArrayList<>();
        for (String s : columns) {
            columnAList.add(s);
        }
        try {
            hmap = Main.relational.get(index).selectMultipleWhere(columnAList, table, where);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<HashMap<String, String>>().setData(hmap);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in multiple columns {columns}(comma separated) in the row in position offset {offset} with the sql option/s where.{where}, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/columns.{columns}/offset.{offset}/where.{where}")
    public Serializable getMultipleWhereOffset(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where) {
        HashMap<String, String> hmap = new HashMap<>();
        ArrayList<String> columnAList = new ArrayList<>();
        for (String s : columns) {
            columnAList.add(s);
        }
        try {
            hmap = Main.relational.get(index).selectMultipleWhereOffset(columnAList, table, where,
                    Integer.parseInt(offset));
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<HashMap<String, String>>().setData(hmap);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in multiple columns {columns}(comma separated) in the row in position offset, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/table.{table}/columns.{columns}/offset.{offset}")
    public Serializable getMultipleOffset(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset) {
        HashMap<String, String> hmap = new HashMap<>();
        ArrayList<String> columnAList = new ArrayList<>();
        for (String s : columns) {
            columnAList.add(s);
        }
        try {
            hmap = Main.relational.get(index).selectMultipleOffset(columnAList, table, Integer.parseInt(offset));
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }
        return new RelationalReturn<HashMap<String, String>>().setData(hmap);
    }

    // MAX
    // @Operation(summary = "from database at index {index}, select from table
    // table.{table} the value in column {column} in the row in position offset
    // {offset} with the sql option/s where.{where}, select {max} rows, if offset
    // does not exist then it returns nothing")

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column {column} in starting at the top, select {max} rows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/column.{column}")
    public Serializable getKeyMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<String> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        try {
            Main.relational.get(index).selectMax(column, table, maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<String>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column {column} starting at the top with the sql option/s where.{where}, select {max} rows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/column.{column}/where.{where}")
    public Serializable getKeyWhereMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<String> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        try {
            Main.relational.get(index).selectWhereMax(column, table, where, maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<String>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column {column} in the row in position offset {offset}, select {max} rows, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/column.{column}/offset.{offset}")
    public Serializable getKeyOffsetMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<String> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        try {
            Main.relational.get(index).selectOffsetMax(column, table, Integer.parseInt(offset), maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<String>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table} the value in column {column} in the row in position offset {offset} with the sql option/s where.{where}, select {max} rows, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListString.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/column.{column}/offset.{offset}/where.where.{where}")
    public Serializable getKeyOffsetWhereMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "column name", required = true) @PathVariable String column,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<String> al = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        try {
            Main.relational.get(index).selectWhereOffsetMax(column, table, where, Integer.parseInt(offset), maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<String>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table}  the value in multiple columns {columns}(comma separated) starting at the top, select {max} rows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListTable.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/columns.{columns}")
    public Serializable getMultipleMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> columnAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : columns) {
            columnAList.add(s);
        }

        try {
            al = Main.relational.get(index).selectMultipleMax(columnAList, table, maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<HashMap<String, String>>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table}  the value in multiple columns {columns}(comma separated) starting at the top, with the sql option/s where.{where}, select {max} rows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListTable.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/columns.{columns}/where.{where}")
    public Serializable getMultipleWhereMax(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> columnAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : columns) {
            columnAList.add(s);
        }

        try {
            al = Main.relational.get(index).selectMultipleWhereMax(columnAList, table, where, maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<HashMap<String, String>>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table}  the value in multiple columns {columns}(comma separated) in the row in position offset {offset} with the sql option/s where.{where}, select {max} rows, if offset does not exist then it returns nothing")
    @GetMapping("/select/{max}/table.{table}/columns.{columns}/offset.{offset}/where.{where}")
    public Serializable getMultipleWhereOffset(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "sql where condition \n ex: \"COLUMN = 0\"", required = true) @PathVariable String where,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> columnAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : columns) {
            columnAList.add(s);
        }

        try {
            al = Main.relational.get(index).selectMultipleWhereOffsetMax(columnAList, table, where,
                    Integer.parseInt(offset), maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<HashMap<String, String>>>().setData(al);
    }

    @Operation(summary = "from database at index {index}, select from table table.{table}  the value in multiple columns {columns}(comma separated) in the row in position offset {offset}, select {max} rows, if offset does not exist then it returns nothing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "table, value, and row exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListTable.class)) }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @GetMapping("/select/{max}/table.{table}/columns.{columns}/offset.{offset}")
    public Serializable getMultipleOffset(
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response,
            @Parameter(description = "array of columns to get", required = true) @PathVariable String[] columns,
            @Parameter(description = "sql OFFSET", required = true) @PathVariable String offset,
            @Parameter(description = "max number of variables to get", required = true) @PathVariable String max) {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        ArrayList<String> columnAList = new ArrayList<>();
        int maxI = Integer.parseInt(max);
        for (String s : columns) {
            columnAList.add(s);
        }

        try {
            al = Main.relational.get(index).selectMultipleOffsetMax(columnAList, table, Integer.parseInt(offset), maxI);
        } catch (NumberFormatException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.BAD_REQUEST.code);
            return new RestError(ResponseCode.BAD_REQUEST);
        }

        return new RelationalReturn<ArrayList<HashMap<String, String>>>().setData(al);
    }

    @Operation(summary = "in the database with index {index}, insert asingle row into table table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "row inserted successfully"),
            @ApiResponse(responseCode = "304", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ListPair.class)) })
    @PostMapping("/insertOne/{table}")
    public Serializable insertOne(@RequestBody EntryPair[] data,
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response) {

        ArrayList<Pair<String, String>> pairlist = Common.hashmapToPairList(Common.entryPairToHashMap(data));

        try {
            Main.relational.get(index).insert(table, pairlist);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED);
        }

        return new RestError(ResponseCode.OK);
    }

    @Operation(summary = "in the database with index {index}, insert asingle row into table table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rows inserted successfully"),
            @ApiResponse(responseCode = "304", description = "bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)) }) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Table.class)) })
    @PostMapping("/insertMany/{table}")
    public Serializable insertMany(@RequestBody EntryPair[][] data,
            @Parameter(description = "the index of the database to make a request to") @PathVariable Integer index,
            @Parameter(description = "sql table name", required = true) @PathVariable String table,
            HttpServletResponse response) {
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
        for (EntryPair[] p : data) {
            dataList.add(Common.entryPairToHashMap(p));
        }

        try {
            Main.relational.get(index).insertMany(table, dataList);
        } catch (DatabaseException e) {
            response.setStatus(ResponseCode.NOT_MODIFIED.code);
            return new RestError(ResponseCode.NOT_MODIFIED);
        }

        return new RestError(ResponseCode.OK);
    }
}
