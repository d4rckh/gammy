import {usePlayerInteractions} from "@/lib/api/queries"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "../ui/table";

export default function PlayerInteractionsList({playerId}: { playerId: number }) {
    const interactions = usePlayerInteractions(playerId);

    return interactions.data && <>
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>Timestamp</TableHead>
                    <TableHead>Interaction</TableHead>
                    <TableHead>Streak</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    interactions.data.map(interaction =>
                        <TableRow>
                            <TableCell>{interaction.id}</TableCell>
                            <TableCell>{interaction.timestamp}</TableCell>
                            <TableCell>{interaction.gameInteraction?.displayName}</TableCell>
                            <TableCell>{interaction.streak}</TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}