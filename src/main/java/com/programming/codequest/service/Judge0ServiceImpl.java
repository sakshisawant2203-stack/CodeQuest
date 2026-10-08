package com.programming.codequest.service;

import com.programming.codequest.dto.Judge0SubmissionRequest;
import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.entity.Submission;
import com.programming.codequest.entity.TestCase;
import com.programming.codequest.repository.SubmissionRepository;
import com.programming.codequest.util.Judge0LanguageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@Service
public class Judge0ServiceImpl implements Judge0Service {

    @Autowired
    private RestClient restClient;

    @Autowired
    private SubmissionRepository SubmissionRepository;

    @Autowired
    private JavaExecutionWrapper javaExecutionWrapper;

    @Autowired
    private CppExecutionWrapper cppExecutionWrapper;

    @Autowired
    private PythonExecutionWrapper pythonExecutionWrapper;

    @Autowired
    private JavaScriptExecutionWrapper javaScriptExecutionWrapper;

    @Value("${judge0.url}")
    private String judge0Url;

    @Value("${judge0.callback-url}")
    private String callbackUrl;

    @Override
    public Judge0SubmissionResponse submit(
            Submission submission,
            TestCase testCase) {

        // =====================================================
        // CREATE SOURCE CODE
        // =====================================================

        String sourceCode;

        if ("java".equalsIgnoreCase(
                submission.getLanguage())) {

            sourceCode =
                    javaExecutionWrapper.generate(
                            submission
                    );

        } else if ("cpp".equalsIgnoreCase(
                submission.getLanguage())) {

            sourceCode =
                    cppExecutionWrapper.generate(
                            submission
                    );

        } else if ("python".equalsIgnoreCase(
                submission.getLanguage())) {

            sourceCode =
                    pythonExecutionWrapper.generate(
                            submission
                    );

        } else if ("javascript".equalsIgnoreCase(
                submission.getLanguage())) {

            sourceCode =
                    javaScriptExecutionWrapper.generate(
                            submission
                    );

        } else {

            throw new RuntimeException(
                    "Unsupported language: "
                            + submission.getLanguage()
            );
        }


        // =====================================================
        // CREATE JUDGE0 REQUEST
        // =====================================================

        Judge0SubmissionRequest request =
                Judge0SubmissionRequest.builder()

                        .source_code(sourceCode)

                        .language_id(
                                Judge0LanguageMapper.getLanguageId(
                                        submission.getLanguage()
                                )
                        )

                        .stdin(
                                testCase.getInput()
                        )

                        .expected_output(
                                testCase.getOutput()
                        )

                        .callback_url(
                                callbackUrl
                        )

                        .build();


        // =====================================================
        // CONVERT REQUEST TO JSON
        // =====================================================

        ObjectMapper mapper =
                new ObjectMapper();

        String json =
                mapper.writeValueAsString(request);


        System.out.println("JSON Sent:");
        System.out.println(json);


        // =====================================================
        // SEND TO JUDGE0
        // =====================================================

        Judge0SubmissionResponse response =
                restClient.post()

                        .uri(
                                judge0Url +
                                        "/submissions?base64_encoded=false&wait=false"
                        )

                        .contentType(
                                MediaType.APPLICATION_JSON
                        )

                        .accept(
                                MediaType.APPLICATION_JSON
                        )

                        .body(json)

                        .retrieve()

                        .body(
                                Judge0SubmissionResponse.class
                        );


        System.out.println(
                "Judge0 Token : "
                        + response.getToken()
        );


        return response;
    }
}