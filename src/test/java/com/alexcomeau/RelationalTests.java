package com.alexcomeau;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;


import com.alexcomeau.config.Database;
import com.alexcomeau.database.relational.Relational;
import com.alexcomeau.database.relational.RelationalFactory;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class RelationalTests {

    @Autowired
	private MockMvc mockMvc;

	@Test
	public void relationalMySqlFunctionalSelectMax() throws Exception {
        Main.relational = new ArrayList<Relational>();
        RelationalFactory rf = new RelationalFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("mysql");
        Main.relational.add(rf.parseDB(db));
		this.mockMvc.perform(get("http://127.0.0.1:8080/rel/select/5/table.test/columns.test,tests")).andDo(print()).andExpect(status().isBadRequest());
	}

    @Test
	public void relationalMySqlFunctionalSelect() throws Exception {
        Main.relational = new ArrayList<Relational>();
        RelationalFactory rf = new RelationalFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("mysql");
        Main.relational.add(rf.parseDB(db));
		this.mockMvc.perform(get("http://127.0.0.1:8080/rel/select/table.test/columns.test,tests")).andDo(print()).andExpect(status().isBadRequest());
	}

    @Test
    public void relationalMysqlFunctionInsert() throws Exception{
        Main.relational = new ArrayList<Relational>();
        RelationalFactory rf = new RelationalFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("mysql");
        Main.relational.add(rf.parseDB(db));
        String json = "{\"test\":\"value\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.post("http://127.0.0.1:8080/rel/insertOne/test")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isNotModified());
    }

    @Test
    public void relationalMysqlFunctionInsertMany() throws Exception{
        Main.relational = new ArrayList<Relational>();
        RelationalFactory rf = new RelationalFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("mysql");
        Main.relational.add(rf.parseDB(db));
       /*
        [
            [
                {
                    "test1":"testValue",
                    "test2":"testValue"
                },
                {
                    "test1":"testValue",
                    "test2":"testValue"
                }
            ]
        ]
        */
        String json = ""+
            "[" + 
                    "{"+
                        "\"test\":\"testValue\","+
                        "\"test2\":\"testValue\"" +
                    "}," +
                    "{"+
                        "\"test\":\"testValue\","+
                        "\"test2\":\"testValue\"" +
                    "}" +
            "]";


        this.mockMvc.perform(MockMvcRequestBuilders.post("http://127.0.0.1:8080/rel/insertMany/test").content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isNotModified());
    }
}
