import {useLeaderboards} from "@/lib/api/queries"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "./ui/table";
import CreateOrEditLeaderboardDialog from "./leaderboards/CreateOrEditLeaderboardDialog";
import DeleteLeaderboardButton from "./leaderboards/DeleteLeaderboardButton";
import ViewLeaderboardEntriesDialog from "./leaderboards/ViewLeaderboardEntriesDialog";

export default function LeaderboardsList() {
    const leaderboards = useLeaderboards();

    return leaderboards.data && <>
        <Table className="mt-2">
            <TableHeader>
                <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>API Name</TableHead>
                    <TableHead>Display Name</TableHead>
                    <TableHead>Description</TableHead>
                    <TableHead>Game Stat</TableHead>
                    <TableHead>Sort Method</TableHead>
                    <TableHead>Actions</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    leaderboards.data.map(ld =>
                        <TableRow>
                            <TableCell>{ld.id}</TableCell>
                            <TableCell>{ld.apiName}</TableCell>
                            <TableCell>{ld.displayName}</TableCell>
                            <TableCell>{ld.description}</TableCell>
                            <TableCell>{ld.gameStat?.apiName}</TableCell>
                            <TableCell>{ld.sortMethod}</TableCell>
                            <TableCell className="flex gap-2 flex-wrap">
                                <ViewLeaderboardEntriesDialog apiName={ld.apiName ?? ""}/>
                                <CreateOrEditLeaderboardDialog leaderboard={ld}/>
                                <DeleteLeaderboardButton lb={ld}/>
                            </TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}