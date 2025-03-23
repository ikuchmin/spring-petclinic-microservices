/*
 * Copyright 2002-2021 the original author or authors.
 *
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
 */
package org.springframework.samples.petclinic.genai;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author Oded Shopen
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GenAIServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenAIServiceApplication.class, args);
	}

    @Bean
    public ApplicationRunner validateProfile(Environment environment) {
        return args -> {
            String[] activeProfiles = environment.getActiveProfiles();
            if (activeProfiles.length == 0 ||
                Arrays.equals(activeProfiles, new String[]{"default"})) {
                throw new IllegalStateException(
                    "Application cannot run with just the 'default' profile. " +
                        "Please specify an environment profile (dev, test, prod, etc.)"
                );
            }
        };
    }
}
