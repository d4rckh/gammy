import {usePlayerStats} from "@/lib/api/queries";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "../ui/table";

export function PlayerStats({playerId}: { playerId: number }) {
    const playerStats = usePlayerStats(playerId);

    return playerStats.data && <>
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>Game Stat</TableHead>
                    <TableHead>Player Value</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    playerStats.data.map(stat =>
                        <TableRow>
                            <TableCell>{stat.gameStat?.apiName}</TableCell>
                            <TableCell>{stat.value}</TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}