package com.takeaway.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.takeaway.challenge.constants.ErrorMessages.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public static void init(){
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Before
    public void deleteAllEmployees() throws Exception {
        mockMvc.perform(delete("http://localhost:8080/employee/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void create_withCompleteValidData_shouldReturnEmployee() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andExpect(jsonPath("$.full_name",is(sampleFullName)))
                .andExpect(jsonPath("$.email",is(sampleEmail)))
                .andExpect(jsonPath("$.birthday",is(sampleBirthdayString)))
               // .andExpect(jsonPath("$.department.id",is(x.getId().toString())))
                .andReturn();
        Thread.sleep(3000);

    }

    @Test
    public void create_withDuplicateEmail_shouldReturnUniqueConstraintError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleFullName2 = "Alex July";
        String sampleBirthdayString = "1979-10-14";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andExpect(jsonPath("$.full_name",is(sampleFullName)))
                .andExpect(jsonPath("$.email",is(sampleEmail)))
                .andExpect(jsonPath("$.birthday",is(sampleBirthdayString)))
                // .andExpect(jsonPath("$.department.id",is(x.getId().toString())))
                .andReturn();

        createEmployee(sampleEmail,sampleFullName2,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith("UNIQUE_CONSTRAINT_EXCEPTION")))
                .andReturn();
    }

    @Test
    public void create_withEmptyEmail_shouldReturnValidationError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = null;
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void create_withNotValidFormatEmail_shouldReturnValidationError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "invalid.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }
    @Test
    public void create_withEmptyFullName_shouldReturnValidationError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "a@b.on";
        String sampleFullName = null;
        String sampleBirthdayString = "1979-10-14";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void create_withEmptyBirthday_shouldReturnValidationError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "a@b.on";
        String sampleFullName = "Allen Max";
        String sampleBirthdayString = "";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void create_withInvalidBirthday_shouldReturnValidationError() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "a@b.on";
        String sampleFullName = "Allen Max";
        String sampleBirthdayString = "1989-13-12";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void create_withEmptyDepId_shouldReturnValidationError() throws Exception {
        //given
        String sampleEmail = "a@b.on";
        String sampleFullName = "Allen Max";
        String sampleBirthdayString = "1989-03-12";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,null)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(DATA_VALIDATION_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void create_withInvalidDepId_shouldReturnUniqueConstraintError() throws Exception {
        //given
        String sampleEmail = "a@b.on";
        String sampleFullName = "Allen Max";
        String sampleBirthdayString = "1989-03-12";

        createEmployee(sampleEmail,sampleFullName,sampleBirthdayString,123456789L)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(UNIQUE_CONSTRAINT_EXCEPTION)))
                .andReturn();
    }

    @Test
    public void get_withExistingId_shouldReturnEmployee() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        EmployeeResponseDto employeeResponseDto = createEmployeeWithResponseDto(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId());
        mockMvc.perform(get("http://localhost:8080/employee/" + employeeResponseDto.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(employeeResponseDto.getId())))
                .andExpect(jsonPath("$.full_name",is(sampleFullName)))
                .andExpect(jsonPath("$.email",is(sampleEmail)))
                .andExpect(jsonPath("$.birthday",is(sampleBirthdayString)))
                // .andExpect(jsonPath("$.department.id",is(x.getId().toString())))
                .andReturn();
    }
    @Test
    public void get_withNonExistingId_shouldReturnNotFound() throws Exception {
        //given
        mockMvc.perform(get("http://localhost:8080/employee/1" ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(ENTITY_NOT_FOUND)))
                .andReturn();
    }

    @Test
    public void update_withExistingId_shouldReturnEmployeeWithUpdatedData() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);
        String sampleName2 = "some Department2";
        DepartmentDto departmentDto2 = createDepartment(sampleName2);

        String sampleEmail = "aaada@bb.com";
        String sampleEmail2 = "aaada2@bb.com";
        String sampleFullName = "John smith";
        String sampleFullName2 = "John smith 2";
        String sampleBirthdayString = "1979-10-14";
        String sampleBirthdayString2 = "1979-10-15";

        EmployeeResponseDto employeeResponseDto = createEmployeeWithResponseDto(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId());

        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().fullName(sampleFullName2)
                .email(sampleEmail2).depId(departmentDto2.getId()).birthday(sampleBirthdayString2).build();

        mockMvc.perform(put("http://localhost:8080/employee/" + employeeResponseDto.getId() ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employeeRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(employeeResponseDto.getId())))
                .andExpect(jsonPath("$.full_name",is(sampleFullName2)))
                .andExpect(jsonPath("$.email",is(sampleEmail2)))
                .andExpect(jsonPath("$.birthday",is(sampleBirthdayString2)))
                // .andExpect(jsonPath("$.department.id",is(x.getId().toString())))
                .andReturn();
    }

    @Test
    public void update_withNonExistingId_shouldReturnNotFound() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "aaada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().fullName(sampleFullName)
                .email(sampleEmail).depId(departmentDto.getId()).birthday(sampleBirthdayString).build();

        mockMvc.perform(put("http://localhost:8080/employee/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employeeRequestDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(ENTITY_NOT_FOUND)))
                .andReturn();

    }

    @Test
    public void delete_withExistingId_shouldReturnNoContent_fetchAgain_shouldReturnNotFound() throws Exception {
        //given
        String sampleName = "some Department";
        DepartmentDto departmentDto = createDepartment(sampleName);

        String sampleEmail = "aaada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";

        EmployeeResponseDto employeeResponseDto = createEmployeeWithResponseDto(sampleEmail,sampleFullName,sampleBirthdayString,departmentDto.getId());

        mockMvc.perform(delete("http://localhost:8080/employee/"+employeeResponseDto.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("http://localhost:8080/employee/"+employeeResponseDto.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(ENTITY_NOT_FOUND)))
                .andReturn();
    }

    @Test
    public void delete_withNonExistingId_shouldReturnNotFound() throws Exception {
        //given
        mockMvc.perform(delete("http://localhost:8080/employee/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",org.hamcrest.Matchers.startsWith(EMPTY_RESULTSET_EXCEPTION)))
                .andReturn();
    }


    public DepartmentDto createDepartment(String departmentName) throws Exception {
        DepartmentDto requestDepartmentDto =   DepartmentDto.builder().name(departmentName).build();
        //when then
        MvcResult mvcResult = mockMvc.perform(post("http://localhost:8080/department").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDepartmentDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andExpect(jsonPath("$.name",is(departmentName)))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, DepartmentDto.class);
    }

    public ResultActions createEmployee(String email,String fullName,String birthday,Long depId) throws Exception {
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().fullName(fullName)
                .email(email).depId(depId).birthday(birthday).build();

        return mockMvc.perform(post("http://localhost:8080/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employeeRequestDto)))
                .andDo(print());
    }

    public EmployeeResponseDto createEmployeeWithResponseDto(String email, String fullName, String birthday, Long depId) throws Exception {
        MvcResult mvcResult = createEmployee(email,fullName, birthday, depId)
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, EmployeeResponseDto.class);
    }
}