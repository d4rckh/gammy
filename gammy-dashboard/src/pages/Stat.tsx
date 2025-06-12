import PageTitle from "@/components/pageTitle";
import { useParams } from "react-router";
import StatsAnalytics from "@/components/stats/StatsAnalytics";

export default function Player() {
  const { apiName } = useParams();

  return (
    <>
      <PageTitle title={`Stat - ${apiName}`} description="Here you can see various insights about your game stat." />

      <StatsAnalytics apiName={apiName ?? ""} />
    </>
  );
}
