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
import org.springframework.test.web.servlet.MockMvc;

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
		this.mockMvc.perform(get("http://127.0.0.1:8080/rel/select/5/table=test&multiple=test,tests")).andDo(print()).andExpect(status().isOk());
	}

    @Test
	public void relationalMySqlFunctionalSelect() throws Exception {
        Main.relational = new ArrayList<Relational>();
        RelationalFactory rf = new RelationalFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("mysql");
        Main.relational.add(rf.parseDB(db));
		this.mockMvc.perform(get("http://127.0.0.1:8080/rel/select/table=test&multiple=test,tests")).andDo(print()).andExpect(status().isOk());
	}
    
}
