import { useParams } from "react-router-dom";

const SearchResults = () => {
  const { term } = useParams();
  console.log(term);
  return (
    <div className="">results</div>
  );
}
export default SearchResults;