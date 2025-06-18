import PageTitle from "@/components/pageTitle";
import LeaderboardsList from "@/components/LeaderboardsList";
import CreateOrEditLeaderboardDialog from "@/components/leaderboards/CreateOrEditLeaderboardDialog";

export default function Leaderboards() {
    return <>
        <PageTitle title="Leaderboards"
            description="Here you can create leaderboards based on the already made game stats." />

        <CreateOrEditLeaderboardDialog />

        <LeaderboardsList />
    </>;
}