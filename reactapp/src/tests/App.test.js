import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import { QueryClient, QueryClientProvider } from 'react-query';
import store from '../store';
import Login from '../Components/Login';
import '@testing-library/jest-dom/extend-expect';
import axios from 'axios';
import Signup from '../Components/Signup';
import ErrorPage from '../Components/ErrorPage';
import HomePage from '../Components/HomePage';
import PetForm from '../PetOwnerComponents/PetForm';
import ApplyAppointment from '../PetOwnerComponents/ApplyAppointment';
import AppliedAppointment from '../PetOwnerComponents/AppliedAppointment';
import UserPostFeedback from '../PetOwnerComponents/UserPostFeedback';
import ViewPet from '../PetOwnerComponents/ViewPet';
import ManagerViewPet from '../ClinicManagerComponents/ManagerViewPet';
import AppointmentDict from '../ClinicManagerComponents/AppointmentDict';
import ViewMyTreatment from '../VeterinarianComponents/ViewMyTreatment';
import TreatmentRecord from '../VeterinarianComponents/TreatmentRecord';

jest.mock('axios');

// Setup QueryClient
const queryClient = new QueryClient();

describe('Login Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderLoginComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <Login {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  
  test('frontend_login_component_renders_the_with_login_heading', () => {
    renderLoginComponent();

  
    const loginHeadings = screen.getAllByText(/Login/i);
    expect(loginHeadings.length).toBeGreaterThan(0);

  });


  test('frontend_login_component_displays_validation_messages_when_login_button_is_clicked_with_empty_fields', () => {
    renderLoginComponent();

    fireEvent.click(screen.getByRole('button', { name: /Login/i }));

    expect(screen.getByText('Email is required')).toBeInTheDocument();
    expect(screen.getByText('Password is required')).toBeInTheDocument();
  });

   
});
describe('Signup Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderSignupComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <Signup {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };
  test('frontend_signup_component_renders_with_signup_heading', () => {
    renderSignupComponent();

    const signupHeadings = screen.getAllByText(/Signup/i);
   expect(signupHeadings.length).toBeGreaterThan(0);

  });

  test('frontend_signup_component_displays_validation_messages_when_submit_button_is_clicked_with_empty_fields', () => {
    renderSignupComponent();

    fireEvent.click(screen.getByRole('button', { name: /Submit/i }));

    expect(screen.getByText('User Name is required')).toBeInTheDocument();
    expect(screen.getByText('Email is required')).toBeInTheDocument();
    expect(screen.getByText('Mobile Number is required')).toBeInTheDocument();
    expect(screen.getByText('Password is required')).toBeInTheDocument();
    expect(screen.getByText('Confirm Password is required')).toBeInTheDocument();
  });

  test('frontend_signup_component_displays_error_when_passwords_do_not_match', () => {
    renderSignupComponent();

    fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'password123' } });
    fireEvent.change(screen.getByPlaceholderText('Confirm Password'), { target: { value: 'password456' } });
    fireEvent.click(screen.getByRole('button', { name: /Submit/i }));

    expect(screen.getByText('Passwords do not match')).toBeInTheDocument();
  });
});

describe('ErrorPage Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });
  const renderErrorComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ErrorPage {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };
  test('frontend_errorpage_component_renders_with_error_heading', () => {
    renderErrorComponent();
    const headingElement = screen.getByText(/Oops! Something Went Wrong/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_errorpage_component_renders_with_error_content', () => {
    renderErrorComponent();
    const paragraphElement = screen.getByText(/Please try again later./i);
    expect(paragraphElement).toBeInTheDocument();
  });
});
describe('Home Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });
  const renderHomeComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <HomePage {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  
  test('frontend_home_component_renders_with_heading', () => {
    renderHomeComponent();
    const headingElement = screen.getAllByText(/VetNest/i);
    expect(headingElement.length).toBeGreaterThan(0);

  });
  test('frontend_home_component_renders_with_contact_us', () => {
    renderHomeComponent();
    const headingElement = screen.getAllByText(/Contact Us/i);
    expect(headingElement.length).toBeGreaterThan(0);

  });
});


describe('PetForm Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderPetFormComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <PetForm {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_petform_petowner_component_renders_with_add_new_pet_heading', () => {
    renderPetFormComponent();
    
    // Check if the heading is present when adding a new pet
    const headingElement = screen.getByText(/Add New Pet/i);
    expect(headingElement).toBeInTheDocument();
  });


  test('frontend_petform_petowner_component_displays_validation_messages_when_submit_button_is_clicked_with_empty_fields', () => {
    renderPetFormComponent();

    // Click the submit button without filling the form
    fireEvent.click(screen.getByRole('button', { name: /Add Pet/i }));

    // Check for validation messages
    expect(screen.getByText('Pet name is required')).toBeInTheDocument();
    expect(screen.getByText('Species is required')).toBeInTheDocument();
    expect(screen.getByText('Breed is required')).toBeInTheDocument();
    expect(screen.getByText('Date of birth is required')).toBeInTheDocument();
  });
});

describe('ApplyAppointment Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderApplyAppointmentComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ApplyAppointment {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_applyappointment_petowner_component_renders_with_add_new_appointment_heading', () => {
    renderApplyAppointmentComponent();

    // Check if the heading is present when adding a new appointment
    const headingElement = screen.getByText(/Add New Appointment/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_applyappointment_petowner_component_displays_validation_messages_when_submit_button_is_clicked_with_empty_fields', () => {
    renderApplyAppointmentComponent();

    // Click the submit button without filling the form
    fireEvent.click(screen.getByRole('button', { name: /Add Appointment/i }));

    // Check for validation messages
    expect(screen.getByText('Appointment date is required')).toBeInTheDocument();
    expect(screen.getByText('Reason is required')).toBeInTheDocument();
  });
});

describe('AppliedAppointment Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderAppliedAppointmentComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <AppliedAppointment {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_appliedappointment_petowner_component_renders_with_heading', () => {
    renderAppliedAppointmentComponent();

    // Check if the heading is present
    const headingElement = screen.getByText(/Appointments/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_appliedappointment_petowner_component_renders_with_table', () => {
    renderAppliedAppointmentComponent();

    // Check if the table is present
    const tableElement = screen.getByRole('table');
    expect(tableElement).toBeInTheDocument();
  });

  test('frontend_appliedappointment_petowner_component_displays_no_records_found_when_no_data', () => {
    renderAppliedAppointmentComponent();

    // Simulate no records found in the table
    const noRecordsText = screen.getByText(/Oops! No records found/i);
    expect(noRecordsText).toBeInTheDocument();
  });

});

describe('UserPostFeedback Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderUserPostFeedbackComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <UserPostFeedback {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_userpostfeedback_petowner_component_renders_with_heading', () => {
    renderUserPostFeedbackComponent();

    // Check if the heading is present
    const headingElement = screen.getByText(/Submit Your Feedback/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_userpostfeedback_petowner_component_displays_validation_message_when_submit_clicked_with_empty_feedback', () => {
    renderUserPostFeedbackComponent();

    // Click the submit button without entering feedback text
    fireEvent.click(screen.getByRole('button', { name: /Submit Feedback/i }));

    // Check for validation message
    expect(screen.getByText('Feedback text is required')).toBeInTheDocument();
  });
});



describe('ViewPet Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderViewPetComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ViewPet {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_viewpet_petowner_component_renders_with_heading', () => {
    renderViewPetComponent();

    // Check if the heading "Pets" is present
    const headingElement = screen.getAllByText(/Pets/i);
    expect(headingElement.length).toBeGreaterThan(0);
  });

  test('frontend_viewpet_petowner_component_renders_with_table', () => {
    renderViewPetComponent();

    // Check if the table is present
    const tableElement = screen.getByRole('table');
    expect(tableElement).toBeInTheDocument();
  });

  test('frontend_viewpet_petowner_component_displays_no_records_found_when_no_data', () => {
    renderViewPetComponent();

    // Simulate no records found in the table
    const noRecordsText = screen.getByText(/Oops! No records found/i);
    expect(noRecordsText).toBeInTheDocument();
  });
});

describe('ManagerViewPet Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderManagerViewPetComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ManagerViewPet {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_managerviewpet_clinicmanager_component_renders_with_heading', () => {
    renderManagerViewPetComponent();

    // Check if the heading "Pets" is present
    const headingElement = screen.getAllByText(/Pets/i);
    expect(headingElement.length).toBeGreaterThan(0);
  });

  test('frontend_managerviewpet_clinicmanager_component_renders_with_table', () => {
    renderManagerViewPetComponent();

    // Check if the table is present
    const tableElement = screen.getByRole('table');
    expect(tableElement).toBeInTheDocument();
  });
});

describe('AppointmentDict Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderAppointmentDictComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <AppointmentDict {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_appointmentdict_clinicmanager_component_renders_with_table', () => {
    renderAppointmentDictComponent();

    // Check if the table is present
    const tableElement = screen.getByRole('table');
    expect(tableElement).toBeInTheDocument();
  });
});

describe('ViewMyTreatment Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderViewMyTreatmentComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ViewMyTreatment {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_viewmytreatment_veterinarian_component_renders_with_heading', () => {
    renderViewMyTreatmentComponent();

    // Check if the heading "My Treatment Records" is present
    const headingElement = screen.getByText(/My Treatment Records/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_viewmytreatment_veterinarian_component_renders_with_table', () => {
    renderViewMyTreatmentComponent();

    // Check if the table is present
    const tableElement = screen.getByRole('table');
    expect(tableElement).toBeInTheDocument();
  });
});

describe('TreatmentRecord Component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  const renderTreatmentRecordComponent = (props = {}) => {
    return render(
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <Router>
            <TreatmentRecord {...props} />
          </Router>
        </QueryClientProvider>
      </Provider>
    );
  };

  test('frontend_treatmentrecord_veterinarian_component_renders_with_heading', () => {
    renderTreatmentRecordComponent();

    // Check if the heading "Add New Treatment Record" is present for adding mode
    const headingElement = screen.getByText(/Add New Treatment Record/i);
    expect(headingElement).toBeInTheDocument();
  });

  test('frontend_treatmentrecord_veterinarian_component_displays_validation_errors_on_empty_submit', () => {
    renderTreatmentRecordComponent();

    // Click the submit button without filling out the form
    const submitButton = screen.getByText(/Add Treatment Record/i);
    fireEvent.click(submitButton);

    // Check for validation error messages
    expect(screen.getByText(/Treatment date is required/i)).toBeInTheDocument();
    expect(screen.getByText(/Description is required/i)).toBeInTheDocument();
    expect(screen.getByText(/Medication is required/i)).toBeInTheDocument();
  });
});