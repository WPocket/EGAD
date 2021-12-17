package com.alexcomeau;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.alexcomeau.config.Database;
import com.alexcomeau.database.keyvalue.KVFactory;
import com.alexcomeau.database.keyvalue.KeyValue;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class KeyValueTests {

    @Autowired
	private MockMvc mockMvc;

	@Test
	public void KeyValueSelect() throws Exception {
        Main.kv = new ArrayList<KeyValue>();
        KVFactory kv = new KVFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("redis");
        Main.kv.add(kv.parseDB(db));
    
		this.mockMvc.perform(get("/kv/get/key.test")).andDo(print()).andExpect(status().isBadRequest());
	}

    @Test
	public void KeyValueSelectIndex() throws Exception {
        Main.kv = new ArrayList<KeyValue>();
        KVFactory kv = new KVFactory();
        Database db = new Database();
        db.setUrl("test1234");
        db.setSupplier("redis");
        Main.kv.add(kv.parseDB(db));
    

		this.mockMvc.perform(get("/kv/0/get/key.test")).andDo(print()).andExpect(status().isBadRequest());
	}
    
}
