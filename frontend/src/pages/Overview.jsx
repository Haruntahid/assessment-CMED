import { useEffect, useState } from "react";
import axios from "axios";

const OverviewTable = () => {
  const [overviewData, setOverviewData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Function to fetch overview data
  const fetchOverviewData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/v1/report");
      setOverviewData(response.data);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching overview data:", error);
      setError("Failed to fetch overview data.");
      setLoading(false);
    }
  };

  // Fetch data when the component mounts
  useEffect(() => {
    fetchOverviewData();
  }, []);

  // Conditional rendering
  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="container mx-auto mt-8">
      <h2 className="text-6xl text-center bg-blue-500 py-6 text-white rounded-lg font-bold mb-4">
        Overview
      </h2>

      <table className="min-w-full bg-white border">
        <thead>
          <tr>
            <th className="py-2 px-4 border">Date</th>
            <th className="py-2 px-4 border">Prescription Count</th>
          </tr>
        </thead>
        <tbody>
          {overviewData.length > 0 ? (
            overviewData.map((data) => (
              <tr key={data.date}>
                <td className="py-2 px-4 border">{data.date}</td>
                <td className="py-2 px-4 border">{data.count}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="2" className="py-2 px-4 border text-center">
                No data available.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default OverviewTable;
