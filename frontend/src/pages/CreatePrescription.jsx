import axios from "axios";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

function CreatePrescription() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  // Automatically set today's date
  const today = new Date().toISOString().split("T")[0];
  const [nextVisitDate, setNextVisitDate] = useState("");
  const navigate = useNavigate();

  const onSubmit = (data) => {
    console.log("Form Data:", data);

    axios
      .post("http://localhost:8080/api/v1/prescriptions", data)
      .then((res) => {
        if (res.data.status == 201) {
          Swal.fire({
            position: "top-center",
            icon: "success",
            title: "Prescription Successfully Created",
            showConfirmButton: false,
            timer: 1500,
          });
          navigate("/prescription");
          reset();
        }
      })
      .catch((error) => {
        if (error.response && error.response.data) {
          // console.log(error.response.data);
          Swal.fire({
            icon: "error",
            title: "Error",
            text: error.response.data?.errors || "Something went wrong!",
          });
        }
      });
  };

  const handleNextVisitDateChange = (e) => {
    setNextVisitDate(e.target.value);
  };

  return (
    <div className="min-h-screen flex justify-center items-center w-full">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-4xl">
        <h2 className="text-6xl text-center bg-blue-500 py-6 text-white rounded-lg font-bold mb-4">
          Create Prescription
        </h2>
        <form onSubmit={handleSubmit(onSubmit)}>
          {/* Prescription Date */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Prescription Date
            </label>
            <input
              type="date"
              value={today}
              readOnly
              {...register("prescriptionDate", { value: today })}
              className="w-full px-4 py-2 border border-gray-400 rounded bg-gray-200 text-gray-700"
            />
          </div>

          {/* Patient Name */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Patient Name</label>
            <input
              type="text"
              {...register("name", {
                required: "Patient name is required",
              })}
              className="w-full px-4 py-2 border border-gray-400 rounded"
            />
            {errors.name && (
              <p className="text-red-500 text-sm">{errors.name.message}</p>
            )}
          </div>

          {/* Patient Age */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Patient Age</label>
            <input
              type="text"
              {...register("age", {
                required: "Patient age is required",
                pattern: {
                  value: /^[1-9][0-9]*$/,
                  message: "Age must be a valid positive number",
                },
                min: {
                  value: 1,
                  message: "Age must be at least 1",
                },
                max: {
                  value: 120,
                  message: "Age cannot be greater than 120",
                },
              })}
              className="w-full px-4 py-2 border border-gray-400 rounded"
              inputMode="numeric"
            />
            {errors.age && (
              <p className="text-red-500 text-sm">{errors.age.message}</p>
            )}
          </div>

          {/* Patient Gender */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Patient Gender</label>
            <select
              {...register("gender", {
                required: "Patient gender is required",
              })}
              className="w-full px-4 py-2 border border-gray-400 rounded"
            >
              <option value="">Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>
            {errors.gender && (
              <p className="text-red-500 text-sm">{errors.gender.message}</p>
            )}
          </div>

          {/* diagnosis */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Diagnosis</label>
            <textarea
              {...register("diagnosis")}
              className="w-full px-4 py-2 border border-gray-400 rounded"
              placeholder="Enter diagnosis"
            />
          </div>

          {/* Medicines */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Medicines</label>
            <textarea
              {...register("medicines")}
              className="w-full px-4 py-2 border border-gray-400 rounded"
              placeholder="Enter medicines"
            />
          </div>

          {/* Next Visit Date */}
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Next Visit Date (Optional)
            </label>
            <input
              type="date"
              {...register("nextVisitDate")}
              value={nextVisitDate || ""}
              onChange={handleNextVisitDateChange}
              className="w-full px-4 py-2 border border-gray-400 rounded"
              min={today}
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600"
          >
            Create Prescription
          </button>
        </form>
      </div>
    </div>
  );
}

export default CreatePrescription;
