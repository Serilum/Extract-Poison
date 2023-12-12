package com.natamus.extractpoison.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.extractpoison.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 3600000) public static int extractDelayMs = 60000;

	public static void initConfig() {
		configMetaData.put("extractDelayMs", Arrays.asList(
			"The delay in ms in between the ability to extract poison per mob."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}