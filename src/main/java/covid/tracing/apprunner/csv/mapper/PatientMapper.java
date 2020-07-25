package covid.tracing.apprunner.csv.mapper;

import covid.tracing.apprunner.csv.domain.Patient;
import covid.tracing.apprunner.csv.domain.PatientDTO;

import java.time.LocalDate;

public class PatientMapper {

//    private static final Logger logger = LoggerFactory.getLogger(new CsvRunner().getClass());

    public boolean isValid(PatientDTO patientDTO) {
        if(patientDTO.getConfPatientId().length() == 0) {
            return false;
        }
        if(patientDTO.getConfirmedDate().length() > 9 ||
                patientDTO.getConfirmedDate().length() == 0) {
            return false;
        }
        return true;
    }

    public Patient convertToPatient(PatientDTO patientDTO) {
        String gender = null;
        if(patientDTO.getGender() == null) {
            gender = "male";
        } else if(patientDTO.getGender().equals("male")) {
            gender = "male";
        } else {
            gender = "female";
        }
        Patient patient = Patient.builder()
                .confirmedDate(this.toLocalDate(patientDTO.getConfirmedDate()))
                .confPatientId(Integer.parseInt(patientDTO.getConfPatientId()))
                .gender(gender)
                .build();

        return patient;
    }

    private LocalDate toLocalDate(String date) {
        String[] dateArr = date.split("/");
        return LocalDate.of(2020, Integer.valueOf(dateArr[0]), Integer.valueOf(dateArr[1]));
    }
}
