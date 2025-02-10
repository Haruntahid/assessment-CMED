import { Link, useLoaderData, useNavigate } from "react-router-dom";
import {
  FaUserCircle,
  FaRegCalendarAlt,
  FaEdit,
  FaTrashAlt,
} from "react-icons/fa";
import { GiMedicines, GiHealthNormal } from "react-icons/gi";
import Swal from "sweetalert2";
import axios from "axios";
import { useContext } from "react";
import { AuthContext } from "../provider/AuthProvider";

function PrescriptionDetails() {
  const { data } = useLoaderData();
  const navigate = useNavigate();
  const { token } = useContext(AuthContext);

  // Placeholder for delete function
  const handleDelete = (id) => {
    Swal.fire({
      title: "Are you sure?",
      text: "You want to delete this Prescription!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`http://localhost:8080/api/v1/prescriptions/${id}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((res) => {
            if (res.data) {
              Swal.fire({
                title: "Deleted!",
                text: "Your Prescription has been deleted.",
                icon: "success",
              });
              navigate("/prescription");
            }
          });
      }
    });
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white rounded-lg shadow-md">
      <div className="flex items-center mb-4">
        <button
          onClick={() => navigate(-1)} // Go back
          className="flex items-center text-blue-500 hover:text-blue-700 mb-4"
        >
          <svg
            className="w-5 h-5 mr-2"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M15 12H9m0 0l6-6m-6 6l6 6"
            />
          </svg>
          Back
        </button>
      </div>

      <h2 className="text-6xl text-center bg-blue-500 py-6 text-white rounded-lg font-bold mb-4">
        Prescription Details
      </h2>

      {/* Patient Information Section */}
      <div className="border-b border-gray-300 pb-4 mb-4">
        <h2 className="text-xl font-semibold flex items-center">
          <FaUserCircle className="mr-2 text-gray-500" />
          Patient Information
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-2">
          <p className="text-lg">
            <strong>Name:</strong> {data.name}
          </p>
          <p className="text-lg">
            <strong>Age:</strong> {data.age} years
          </p>
          <p className="text-lg">
            <strong>Gender:</strong>{" "}
            <span className="capitalize">{data.gender}</span>
          </p>
        </div>
      </div>

      {/* Date Section */}
      <div className="border-b border-gray-300 pb-4 mb-4">
        <h2 className="text-xl font-semibold flex items-center">
          <FaRegCalendarAlt className="mr-2 text-gray-500" />
          Dates
        </h2>
        <div className="grid grid-cols-2 gap-4 mt-2">
          <div>
            <strong>Prescription Date:</strong>
            <p className="mt-1 text-lg">
              {new Date(data.prescriptionDate).toLocaleDateString()}
            </p>
          </div>
          {data.nextVisitDate ? (
            <div>
              <strong>Next Visit Date:</strong>
              <p className="mt-1 text-lg">
                {new Date(data.nextVisitDate).toLocaleDateString()}
              </p>
            </div>
          ) : (
            <div className="text-gray-400">
              <strong>Next Visit Date:</strong>
              <p className="mt-1 text-lg">Not scheduled</p>
            </div>
          )}
        </div>
      </div>

      {/* Diagnosis Section */}
      <div className="border-b border-gray-300 pb-4 mb-4">
        <h2 className="text-xl font-semibold flex items-center">
          <GiHealthNormal className="mr-2 text-gray-500" />
          Diagnosis
        </h2>
        <p className="mt-2 text-lg">{data.diagnosis}</p>
      </div>

      {/* Medicines Section */}
      <div>
        <h2 className="text-xl font-semibold flex items-center">
          <GiMedicines className="mr-2 text-gray-500" />
          Medicines Prescribed
        </h2>
        <p className="mt-2 text-lg">{data.medicines}</p>
      </div>

      {/* Action Buttons: Edit, Delete */}
      <div className="flex justify-end mt-8 space-x-4">
        <Link
          to={`/edit-prescription/${data.id}`}
          className="flex items-center px-3 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600"
        >
          <FaEdit className="mr-2" />
          Edit
        </Link>
        <button
          onClick={() => handleDelete(data.id)}
          className="flex items-center px-3 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
        >
          <FaTrashAlt className="mr-2" />
          Delete
        </button>
      </div>
    </div>
  );
}

export default PrescriptionDetails;
