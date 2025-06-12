import { useGameStats, usePlayersQuery } from "@/lib/api/queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";
import CreateOrEditStatDialog from "./stats/CreateOrEditStatDialog";
import DeleteStatsButton from "./stats/DeleteStatsButton";
import { Dialog, DialogContent, DialogTitle, DialogTrigger } from "./ui/dialog";
import { Button } from "./ui/button";
import StatsAnalytics from "./stats/StatsAnalytics";
import { NavLink } from "react-router";

export default function GameStatsList() {
  const gameStats = useGameStats();

  return gameStats.data && <>
    <Table className="mt-2">
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>API Name</TableHead>
          <TableHead>Display Name</TableHead>
          <TableHead>Type</TableHead>
          <TableHead>Description</TableHead>
          <TableHead>Default value</TableHead>
          <TableHead>Max value</TableHead>
          <TableHead>Min value</TableHead>
          <TableHead>Max change</TableHead>
          <TableHead>Only increment?</TableHead>
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          gameStats.data.map(stat =>
            <TableRow>
              <TableCell>{stat.id}</TableCell>
              <TableCell>{stat.apiName}</TableCell>
              <TableCell>{stat.displayName}</TableCell>
              <TableCell>{stat.type}</TableCell>
              <TableCell>{stat.description}</TableCell>
              <TableCell>{stat.defaultValue}</TableCell>
              <TableCell>{stat.maxValue}</TableCell>
              <TableCell>{stat.minValue}</TableCell>
              <TableCell>{stat.maxChange}</TableCell>
              <TableCell>{stat.onlyIncrement ? "yes" : "no"}</TableCell>
              <TableCell className="flex flex-wrap gap-2">
                <NavLink to={`/stats/${stat.apiName}`}>
                  <Button variant={"outline"} size={"sm"}>Analytics</Button>
                </NavLink>
                <CreateOrEditStatDialog stat={stat} />
                <DeleteStatsButton stat={stat} />
              </TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}