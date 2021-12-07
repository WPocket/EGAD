package com.alexcomeau;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.alexcomeau.database.keyvalue.KeyValue;
import com.alexcomeau.database.keyvalue.redis.Redis;

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
        Main.kv.add(new Redis(null));
    
		this.mockMvc.perform(get("/kv/get/key.test")).andDo(print()).andExpect(status().isBadRequest());
	}

    @Test
	public void KeyValueSelectIndex() throws Exception {
        Main.kv = new ArrayList<KeyValue>();
        Main.kv.add(new Redis(null));

		this.mockMvc.perform(get("/kv/0/get/key.test")).andDo(print()).andExpect(status().isBadRequest());
	}
    
}
