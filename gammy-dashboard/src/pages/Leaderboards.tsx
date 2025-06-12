import PageTitle from "@/components/pageTitle";
import LeaderboardsList from "@/components/LeaderboardsList";
import CreateOrEditLeaderboardDialog from "@/components/leaderboards/CreateOrEditLeaderboardDialog";

export default function Leaderboards() {
  return <>
    <PageTitle title="Leaderboards" />

    <CreateOrEditLeaderboardDialog />

    <LeaderboardsList />
  </>;
}