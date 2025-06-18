import {useLeaderboardEntries} from "@/lib/api/queries";
import {Dialog, DialogContent, DialogTitle, DialogTrigger} from "../ui/dialog";
import {Button} from "../ui/button";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "../ui/table";

export default function ViewLeaderboardEntriesDialog({apiName}: { apiName: string }) {

    const leaderboardEntries = useLeaderboardEntries(apiName);

    return <>
        <Dialog>
            <DialogTrigger asChild>
                <Button variant={"outline"} size={"sm"}>Entries</Button>
            </DialogTrigger>
            <DialogContent>
                <DialogTitle>Leaderboard Entries</DialogTitle>

                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Rank</TableHead>
                            <TableHead>Player</TableHead>
                            <TableHead>Value</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {
                            leaderboardEntries.data &&
                            leaderboardEntries.data.entries.map(entry =>
                                <TableRow>
                                    <TableCell>{entry.rank}</TableCell>
                                    <TableCell>{entry.player.username}</TableCell>
                                    <TableCell>{entry.value}</TableCell>
                                </TableRow>
                            )
                        }
                    </TableBody>
                </Table>
            </DialogContent>
        </Dialog>
    </>

}